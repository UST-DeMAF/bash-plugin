package ust.tad.bashplugin.analysis;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;
import ust.tad.bashplugin.models.tsdm.DeploymentModelContent;
import ust.tad.bashplugin.models.tsdm.InvalidAnnotationException;
import ust.tad.bashplugin.models.tsdm.InvalidNumberOfLinesException;
import ust.tad.bashplugin.models.tsdm.Line;
import ust.tad.bashplugin.models.tsdm.TechnologySpecificDeploymentModel;

@Service
public class HelmAnalyzer {

  private static List<String> repoCommands = new ArrayList<>();

  public TechnologySpecificDeploymentModel analyzeHelmCommand(
      String technology,
      String lineContent,
      TechnologySpecificDeploymentModel parentDeploymentModel,
      URL currentDirectory)
      throws InvalidAnnotationException, InvalidNumberOfLinesException {

    String[] tokens = lineContent.split(" ");
    for (String token : tokens) {
      if (token.equals("install")) {
        DeploymentModelContent deploymentModelContent = new DeploymentModelContent();
        deploymentModelContent.setLocation(currentDirectory);

        List<Line> lines = new ArrayList<>();
        Line line = new Line();
        line.setNumber(0);
        line.setAnalyzed(false);
        line.setComprehensibility(0D);
        lines.add(line);
        deploymentModelContent.setLines(lines);

        TechnologySpecificDeploymentModel embeddedDeploymentModel =
            new TechnologySpecificDeploymentModel();
        embeddedDeploymentModel.setTransformationProcessId(
            parentDeploymentModel.getTransformationProcessId());
        embeddedDeploymentModel.setTechnology(technology);
        List<String> commands = new ArrayList<>();
        commands.addAll(HelmAnalyzer.repoCommands);
        commands.add(lineContent);
        embeddedDeploymentModel.setCommands(commands);
        embeddedDeploymentModel.addDeploymentModelContent(deploymentModelContent);
        return embeddedDeploymentModel;
      } else if (token.equals("repo")) {
        HelmAnalyzer.repoCommands.add(lineContent);
      }
    }
    return null;
  }
}
