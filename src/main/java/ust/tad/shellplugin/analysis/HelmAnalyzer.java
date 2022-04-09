package ust.tad.shellplugin.analysis;

import java.util.List;

import org.springframework.stereotype.Service;

import ust.tad.shellplugin.models.tsdm.TechnologySpecificDeploymentModel;

@Service
public class HelmAnalyzer {

    public TechnologySpecificDeploymentModel analyzeHelmCommand(String technology, String lineContent, TechnologySpecificDeploymentModel parentDeploymentModel) {

        String[] tokens = lineContent.split(" ");
        for(String token : tokens) {
            if(token.equals("install")) {
                TechnologySpecificDeploymentModel embeddedDeploymentModel = new TechnologySpecificDeploymentModel();
                embeddedDeploymentModel.setTransformationProcessId(parentDeploymentModel.getTransformationProcessId());
                embeddedDeploymentModel.setTechnology(technology);
                embeddedDeploymentModel.setCommands(List.of(lineContent));
                return embeddedDeploymentModel;
            }
        }
        return null;              
    }
    
}
