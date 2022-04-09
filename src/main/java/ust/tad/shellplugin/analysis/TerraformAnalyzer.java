package ust.tad.shellplugin.analysis;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import org.springframework.stereotype.Service;

import ust.tad.shellplugin.models.tsdm.DeploymentModelContent;
import ust.tad.shellplugin.models.tsdm.InvalidAnnotationException;
import ust.tad.shellplugin.models.tsdm.InvalidNumberOfLinesException;
import ust.tad.shellplugin.models.tsdm.Line;
import ust.tad.shellplugin.models.tsdm.TechnologySpecificDeploymentModel;

@Service
public class TerraformAnalyzer {

    public TechnologySpecificDeploymentModel analyzeTerraformCommand(String technology, String lineContent, TechnologySpecificDeploymentModel parentDeploymentModel, URL currentDirectory) throws MalformedURLException, InvalidAnnotationException, InvalidNumberOfLinesException {
        URL location = new URL("");

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
    
            Line line = new Line();
            line.setNumber(0);
            line.setComprehensibility(1D);
            line.setAnalyzed(true);
            deploymentModelContent.setLines(List.of(line));
    
            TechnologySpecificDeploymentModel embeddedDeploymentModel = new TechnologySpecificDeploymentModel();
            embeddedDeploymentModel.setTransformationProcessId(parentDeploymentModel.getTransformationProcessId());
            embeddedDeploymentModel.setTechnology(technology);
            embeddedDeploymentModel.setCommands(List.of(lineContent));
            embeddedDeploymentModel.addDeploymentModelContent(deploymentModelContent);
    
            return embeddedDeploymentModel;
        } else {
            existingEmbeddedModel.addCommand(lineContent);
            return existingEmbeddedModel;
        }        
    }
    
}
