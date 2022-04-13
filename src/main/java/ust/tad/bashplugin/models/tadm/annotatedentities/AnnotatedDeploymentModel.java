package ust.tad.bashplugin.models.tadm.annotatedentities;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

import ust.tad.bashplugin.models.tadm.entities.Component;
import ust.tad.bashplugin.models.tadm.entities.ComponentType;
import ust.tad.bashplugin.models.tadm.entities.DeploymentModel;
import ust.tad.bashplugin.models.tadm.entities.Property;
import ust.tad.bashplugin.models.tadm.entities.Relation;
import ust.tad.bashplugin.models.tadm.entities.RelationType;

public class AnnotatedDeploymentModel extends DeploymentModel{

    private UUID id = UUID.randomUUID();
    
    private UUID transformationProcessId;
    

    public AnnotatedDeploymentModel() {
        super();
    }

    public AnnotatedDeploymentModel(List<Property> properties, List<Component> components, List<Relation> relations, List<ComponentType> componentTypes, List<RelationType> relationTypes, UUID transformationProcessId) {
        super(properties, components, relations, componentTypes, relationTypes);
        this.transformationProcessId = transformationProcessId;
    }
    
    public UUID getId() {
        return this.id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getTransformationProcessId() {
        return this.transformationProcessId;
    }

    public void setTransformationProcessId(UUID transformationProcessId) {
        this.transformationProcessId = transformationProcessId;
    }
    
    public AnnotatedDeploymentModel id(UUID id) {
        setId(id);
        return this;
    }

    public AnnotatedDeploymentModel transformationProcessId(UUID transformationProcessId) {
        setTransformationProcessId(transformationProcessId);
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof AnnotatedDeploymentModel)) {
            return false;
        }
        AnnotatedDeploymentModel annotatedDeploymentModel = (AnnotatedDeploymentModel) o;
        return Objects.equals(getProperties(), annotatedDeploymentModel.getProperties()) 
            && Objects.equals(getComponents(), annotatedDeploymentModel.getComponents()) 
            && Objects.equals(getRelations(), annotatedDeploymentModel.getRelations()) 
            && Objects.equals(getComponentTypes(), annotatedDeploymentModel.getComponentTypes()) 
            && Objects.equals(getRelationTypes(), annotatedDeploymentModel.getRelationTypes())
            && Objects.equals(id, annotatedDeploymentModel.id)
            && Objects.equals(transformationProcessId, annotatedDeploymentModel.transformationProcessId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(getProperties(), getComponents(), getRelations(), getComponentTypes(), getRelationTypes(), id, transformationProcessId);
    }

    @Override
    public String toString() {
        return "{" +
            " properties='" + getProperties() + "'" +
            ", components='" + getComponents() + "'" +
            ", relations='" + getRelations() + "'" +
            ", componentTypes='" + getComponentTypes() + "'" +
            ", relationTypes='" + getRelationTypes() + "'" +
            ", id='" + getId() + "'" +
            ", transformationProcessId='" + getTransformationProcessId() + "'" +
            "}";
    }
    

}
