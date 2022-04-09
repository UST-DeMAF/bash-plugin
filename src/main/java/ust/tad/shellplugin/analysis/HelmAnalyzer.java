package ust.tad.shellplugin.analysis;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import ust.tad.shellplugin.models.tsdm.DeploymentModelContent;
import ust.tad.shellplugin.models.tsdm.InvalidAnnotationException;
import ust.tad.shellplugin.models.tsdm.InvalidNumberOfLinesException;
import ust.tad.shellplugin.models.tsdm.Line;
import ust.tad.shellplugin.models.tsdm.TechnologySpecificDeploymentModel;

@Service
public class HelmAnalyzer {

    public TechnologySpecificDeploymentModel analyzeHelmCommand(String technology, String lineContent, TechnologySpecificDeploymentModel parentDeploymentModel, URL currentDirectory) throws InvalidAnnotationException, InvalidNumberOfLinesException {

        String[] tokens = lineContent.split(" ");
        for(String token : tokens) {
            if(token.equals("install")) {
                DeploymentModelContent deploymentModelContent = new DeploymentModelContent();
                deploymentModelContent.setLocation(currentDirectory);
        
                List<Line> lines = new ArrayList<>();
                Line line = new Line();
                line.setNumber(0);
                line.setAnalyzed(true);
                line.setComprehensibility(1D);
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
            }
        }
        return null;              
    }
    
}
