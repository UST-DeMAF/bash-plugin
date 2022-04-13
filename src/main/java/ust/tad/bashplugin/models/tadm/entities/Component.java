package ust.tad.bashplugin.models.tadm.entities;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Component extends ModelElement{

    private ComponentType type;

    private List<Artifact> artifacts = new ArrayList<>();


    public Component() {
        super();
    }

    public Component(String name, String description, List<Property> properties, List<Operation> operations, ComponentType type, List<Artifact> artifacts) {
        super(name, description, properties, operations);
        this.type = type;
        this.artifacts = artifacts;
    }
    
    public ComponentType getType() {
        return this.type;
    }

    public void setType(ComponentType type) {
        this.type = type;
    }

    public List<Artifact> getArtifacts() {
        return this.artifacts;
    }

    public void setArtifacts(List<Artifact> artifacts) {
        this.artifacts = artifacts;
    }
    
    public Component type(ComponentType type) {
        setType(type);
        return this;
    }

    public Component artifacts(List<Artifact> artifacts) {
        setArtifacts(artifacts);
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof Component)) {
            return false;
        }
        Component component = (Component) o;
        return Objects.equals(getId(), component.getId()) 
            && Objects.equals(getName(), component.getName()) 
            && Objects.equals(getDescription(), component.getDescription()) 
            && Objects.equals(getProperties(), component.getProperties()) 
            && Objects.equals(getOperations(), component.getOperations())
            && Objects.equals(type, component.type)
            && Objects.equals(artifacts, component.artifacts);
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getName(), getDescription(), getProperties(), getOperations(), type, artifacts);
    }

    @Override
    public String toString() {
        return "{" +
            " id='" + getId() + "'" +
            ", name='" + getName() + "'" +
            ", type='" + getType() + "'" +            
            ", description='" + getDescription() + "'" +
            ", properties='" + getProperties() + "'" +
            ", operations='" + getOperations() + "'" +
            ", artifacts='" + getArtifacts() + "'" +
            "}";
    }

    
}
