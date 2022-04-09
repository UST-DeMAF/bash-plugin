package ust.tad.shellplugin.models.tadm.entities;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class DeploymentModel {

    private List<Property> properties = new ArrayList<>();

    private List<Component> components = new ArrayList<>();

    private List<Relation> relations = new ArrayList<>();

    private List<ComponentType> componentTypes = new ArrayList<>();

    private List<RelationType> relationTypes = new ArrayList<>();


    public DeploymentModel() {
    }

    public DeploymentModel(List<Property> properties, List<Component> components, List<Relation> relations, List<ComponentType> componentTypes, List<RelationType> relationTypes) {
        this.properties = properties;
        this.components = components;
        this.relations = relations;
        this.componentTypes = componentTypes;
        this.relationTypes = relationTypes;
    }

    public List<Property> getProperties() {
        return this.properties;
    }

    public void setProperties(List<Property> properties) {
        this.properties = properties;
    }

    public List<Component> getComponents() {
        return this.components;
    }

    public void setComponents(List<Component> components) {
        this.components = components;
    }

    public List<Relation> getRelations() {
        return this.relations;
    }

    public void setRelations(List<Relation> relations) {
        this.relations = relations;
    }

    public List<ComponentType> getComponentTypes() {
        return this.componentTypes;
    }

    public void setComponentTypes(List<ComponentType> componentTypes) {
        this.componentTypes = componentTypes;
    }

    public List<RelationType> getRelationTypes() {
        return this.relationTypes;
    }

    public void setRelationTypes(List<RelationType> relationTypes) {
        this.relationTypes = relationTypes;
    }

    public DeploymentModel properties(List<Property> properties) {
        setProperties(properties);
        return this;
    }

    public DeploymentModel components(List<Component> components) {
        setComponents(components);
        return this;
    }

    public DeploymentModel relations(List<Relation> relations) {
        setRelations(relations);
        return this;
    }

    public DeploymentModel componentTypes(List<ComponentType> componentTypes) {
        setComponentTypes(componentTypes);
        return this;
    }

    public DeploymentModel relationTypes(List<RelationType> relationTypes) {
        setRelationTypes(relationTypes);
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof DeploymentModel)) {
            return false;
        }
        DeploymentModel deploymentModel = (DeploymentModel) o;
        return Objects.equals(properties, deploymentModel.properties) 
            && Objects.equals(components, deploymentModel.components) 
            && Objects.equals(relations, deploymentModel.relations) 
            && Objects.equals(componentTypes, deploymentModel.componentTypes) 
            && Objects.equals(relationTypes, deploymentModel.relationTypes);
    }

    @Override
    public int hashCode() {
        return Objects.hash(properties, components, relations, componentTypes, relationTypes);
    }

    @Override
    public String toString() {
        return "{" +
            " properties='" + getProperties() + "'" +
            ", components='" + getComponents() + "'" +
            ", relations='" + getRelations() + "'" +
            ", componentTypes='" + getComponentTypes() + "'" +
            ", relationTypes='" + getRelationTypes() + "'" +
            "}";
    }
    
}
