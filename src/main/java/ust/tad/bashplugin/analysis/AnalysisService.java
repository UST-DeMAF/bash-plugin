package ust.tad.bashplugin.analysis;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import ust.tad.bashplugin.analysistask.AnalysisTaskResponseSender;
import ust.tad.bashplugin.analysistask.Location;
import ust.tad.bashplugin.models.ModelsService;
import ust.tad.bashplugin.models.tadm.TechnologyAgnosticDeploymentModel;
import ust.tad.bashplugin.models.tsdm.DeploymentModelContent;
import ust.tad.bashplugin.models.tsdm.InvalidAnnotationException;
import ust.tad.bashplugin.models.tsdm.InvalidNumberOfContentException;
import ust.tad.bashplugin.models.tsdm.InvalidNumberOfLinesException;
import ust.tad.bashplugin.models.tsdm.Line;
import ust.tad.bashplugin.models.tsdm.TechnologySpecificDeploymentModel;

@Service
public class AnalysisService {

  /**
   * These are the detectable technologies of the plugin. The keys are the shell methods. The values
   * are the names of the technologies.
   */
  private static final Map<String, String> detectableTechnologies =
      Map.of(
          "terraform", "terraform",
          "kubectl", "kubernetes",
          "helm", "helm");

  private static final List<String> ignorableCommands = List.of("az");

  @Autowired ModelsService modelsService;

  @Autowired AnalysisTaskResponseSender analysisTaskResponseSender;

  @Autowired TerraformAnalyzer terraformAnalyzer;

  @Autowired KubernetesAnalyzer kubernetesAnalyzer;

  @Autowired HelmAnalyzer helmAnalyzer;

  private TechnologySpecificDeploymentModel tsdm;

  private TechnologyAgnosticDeploymentModel tadm;

  private Set<Integer> newEmbeddedDeploymentModelIndexes = new HashSet<>();

  /**
   * Start the analysis of the deployment model. 1. Retrieve deployment models from models service
   * 2. Parse in technology-specific deployment model from locations 3. Update tsdm with new
   * information 4. Transform to EDMM entities and update tadm 5. Send updated models to models
   * service 6. Send AnalysisTaskResponse and EmbeddedDeploymentModelAnalysisRequests if present
   *
   * @param taskId
   * @param transformationProcessId
   * @param commands
   * @param locations
   * @throws InvalidAnnotationException
   * @throws InvalidNumberOfLinesException
   * @throws IOException
   * @throws URISyntaxException
   */
  public void startAnalysis(
      UUID taskId,
      UUID transformationProcessId,
      List<String> commands,
      List<String> options,
      List<Location> locations) {
    clearVariables();
    this.tsdm = modelsService.getTechnologySpecificDeploymentModel(transformationProcessId);
    this.tadm = modelsService.getTechnologyAgnosticDeploymentModel(transformationProcessId);

    try {
      runAnalysis(commands, locations);
    } catch (NullPointerException
        | URISyntaxException
        | IOException
        | InvalidNumberOfLinesException
        | InvalidAnnotationException
        | InvalidNumberOfContentException e) {
      e.printStackTrace();
      analysisTaskResponseSender.sendFailureResponse(taskId, e.getClass() + e.getMessage());
      return;
    }

    updateDeploymentModels(this.tsdm, this.tadm);

    if (newEmbeddedDeploymentModelIndexes.isEmpty()) {
      analysisTaskResponseSender.sendSuccessResponse(taskId);
    } else {
      for (int index : newEmbeddedDeploymentModelIndexes) {
        analysisTaskResponseSender.sendEmbeddedDeploymentModelAnalysisRequestFromModel(
            this.tsdm.getEmbeddedDeploymentModels().get(index), taskId);
      }
      analysisTaskResponseSender.sendSuccessResponse(taskId);
    }
  }

  private void updateDeploymentModels(
      TechnologySpecificDeploymentModel tsdm, TechnologyAgnosticDeploymentModel tadm) {
    modelsService.updateTechnologySpecificDeploymentModel(tsdm);
    modelsService.updateTechnologyAgnosticDeploymentModel(tadm);
  }

  /**
   * Iterate of the locations and parse in all files that can be found. The file has to have the
   * fileextension ".sh", otherwise it will be ignored. If the given location is a directory,
   * iterate over all contained files. Removes the deployment model content associated with the old
   * directory locations because it has been resolved to the contained files.
   *
   * @param commands
   * @param locations
   * @throws URISyntaxException
   * @throws IOException
   * @throws InvalidNumberOfLinesException
   * @throws InvalidAnnotationException
   * @throws InvalidNumberOfContentException
   */
  private void runAnalysis(List<String> commands, List<Location> locations)
      throws URISyntaxException,
          IOException,
          InvalidNumberOfLinesException,
          InvalidAnnotationException,
          InvalidNumberOfContentException {
    for (Location location : locations) {
      if ("file".equals(location.getUrl().getProtocol())
          && new File(location.getUrl().toURI()).isDirectory()) {
        File directory = new File(location.getUrl().toURI());
        for (File file : directory.listFiles()) {
          if ("sh".equals(StringUtils.getFilenameExtension(file.toURI().toURL().toString()))) {
            analyzeFile(file.toURI().toURL());
          }
        }
        DeploymentModelContent contentToRemove = new DeploymentModelContent();
        for (DeploymentModelContent content : this.tsdm.getContent()) {
          if (content.getLocation().equals(location.getUrl())) {
            contentToRemove = content;
          }
        }
        this.tsdm.removeDeploymentModelContent(contentToRemove);
      } else {
        if ("sh".equals(StringUtils.getFilenameExtension(location.getUrl().toString()))) {
          analyzeFile(location.getUrl());
        } else {
        }
      }
    }
  }

  /**
   * Analyzes a file and creates a corresponding DeploymentModelContent. Iterates over the lines in
   * the file and adds corresponding Line entities to the DeploymentModelContent. In the end it adds
   * the DeploymentModelContent to the technology-agnostic deployment model.
   *
   * @param url
   * @throws IOException
   * @throws InvalidNumberOfLinesException
   * @throws InvalidAnnotationException
   */
  private void analyzeFile(URL url)
      throws IOException, InvalidNumberOfLinesException, InvalidAnnotationException {
    DeploymentModelContent deploymentModelContent = new DeploymentModelContent();
    deploymentModelContent.setLocation(url);

    int endIndex = url.toString().lastIndexOf("/");
    URL currentDirectory = new URL(url.toString().substring(0, endIndex + 1));

    List<Line> lines = new ArrayList<>();
    int lineNumber = 1;
    BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));
    while (reader.ready()) {
      Line line = analyzeLine(reader.readLine(), lineNumber, currentDirectory);
      if (line != null) {
        lines.add(line);
      }
      lineNumber++;
    }
    reader.close();

    deploymentModelContent.setLines(lines);
    this.tsdm.addDeploymentModelContent(deploymentModelContent);
  }

  /**
   * Analyze a line of a file. If it is blank or a comment it is not added. Some commands do not
   * contain useful information but are added as comprehended. When a command of a detectable
   * deployment technology is found, an embedded deplyoment model is added.
   *
   * @param lineContent
   * @param lineNumber
   * @return
   * @throws InvalidAnnotationException
   * @throws InvalidNumberOfLinesException
   * @throws MalformedURLException
   */
  private Line analyzeLine(String lineContent, int lineNumber, URL currentDirectory)
      throws InvalidAnnotationException, MalformedURLException, InvalidNumberOfLinesException {
    Line line = new Line();
    line.setNumber(lineNumber);

    if (lineContent.isBlank() || lineContent.startsWith("#")) {
      return null;
    } else {
      String method = lineContent.split(" ")[0];
      if (ignorableCommands.contains(method)) {
        line.setAnalyzed(true);
        line.setComprehensibility(1D);
      } else if (detectableTechnologies.containsKey(method)) {
        line.setAnalyzed(true);
        line.setComprehensibility(1D);
        addEmbeddedDeploymentModel(
            detectableTechnologies.get(method), lineContent, currentDirectory);
      } else {
        line.setAnalyzed(true);
        line.setComprehensibility(0D);
      }
      return line;
    }
  }

  /**
   * Add a new embedded deployment model to the technology-specific deployment model. Based on the
   * technology that was found, call an appropriate analyzer for that technology. Marks the index of
   * the new embedded deployment model to later send an EmbeddedDeploymentModelAnalysisRequest.
   *
   * @param technology
   * @param lineContent
   * @param currentDirectory
   * @throws MalformedURLException
   * @throws InvalidAnnotationException
   * @throws InvalidNumberOfLinesException
   */
  private void addEmbeddedDeploymentModel(
      String technology, String lineContent, URL currentDirectory)
      throws MalformedURLException, InvalidAnnotationException, InvalidNumberOfLinesException {
    TechnologySpecificDeploymentModel embeddedDeploymentModel;
    switch (technology) {
      case "terraform":
        embeddedDeploymentModel =
            terraformAnalyzer.analyzeTerraformCommand(
                technology, lineContent, this.tsdm, currentDirectory);
        break;
      case "kubernetes":
        embeddedDeploymentModel =
            kubernetesAnalyzer.analyzeKubernetesCommand(
                technology, lineContent, this.tsdm, currentDirectory);
        break;
      case "helm":
        embeddedDeploymentModel =
            helmAnalyzer.analyzeHelmCommand(technology, lineContent, this.tsdm, currentDirectory);
        break;
      default:
        return;
    }

    if (embeddedDeploymentModel != null) {
      int index = this.tsdm.addOrUpdateEmbeddedDeploymentModel(embeddedDeploymentModel);
      this.newEmbeddedDeploymentModelIndexes.add(index);
    }
  }

  /**
   * Clears the variables and resources set to avoid side effects between different transformation
   * processes.
   */
  private void clearVariables() {
    newEmbeddedDeploymentModelIndexes.clear();
  }
}
