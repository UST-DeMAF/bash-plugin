package ust.tad.bashplugin.analysis;

import java.net.MalformedURLException;
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
public class TerraformAnalyzer {

    public TechnologySpecificDeploymentModel analyzeTerraformCommand(String technology, String lineContent, TechnologySpecificDeploymentModel parentDeploymentModel, URL currentDirectory) throws MalformedURLException, InvalidAnnotationException, InvalidNumberOfLinesException {
        URL location = currentDirectory;

        String[] tokens = lineContent.split(" ");
        for(String token : tokens) {
            if(token.startsWith("-chdir")) {
                String chdir = token.split("=")[1];
                location = new URL(currentDirectory.toString()+chdir);
            }
        }

        TechnologySpecificDeploymentModel existingEmbeddedModel = parentDeploymentModel.getEmbeddedModelByLocation(location);
        if (existingEmbeddedModel == null) {
            DeploymentModelContent deploymentModelContent = new DeploymentModelContent();
            deploymentModelContent.setLocation(location);
    
            List<Line> lines = new ArrayList<>();
            Line line = new Line();
            line.setNumber(0);
            line.setAnalyzed(false);
            line.setComprehensibility(0D);
            lines.add(line);
            deploymentModelContent.setLines(lines);
    
            TechnologySpecificDeploymentModel embeddedDeploymentModel = new TechnologySpecificDeploymentModel();
            embeddedDeploymentModel.setTransformationProcessId(parentDeploymentModel.getTransformationProcessId());
            embeddedDeploymentModel.setTechnology(technology);
            List<String> commands = new ArrayList<>();
            commands.add(lineContent);
            embeddedDeploymentModel.setCommands(commands);
            embeddedDeploymentModel.addDeploymentModelContent(deploymentModelContent);
    
            return embeddedDeploymentModel;
        } else {
            existingEmbeddedModel.addCommand(lineContent);
            return existingEmbeddedModel;
        }        
    }
    
}
