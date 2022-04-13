package ust.tad.bashplugin.models.tadm.annotatedentities;

import java.util.List;
import java.util.Objects;

import ust.tad.bashplugin.models.tadm.entities.Artifact;
import ust.tad.bashplugin.models.tadm.entities.Component;
import ust.tad.bashplugin.models.tadm.entities.ComponentType;
import ust.tad.bashplugin.models.tadm.entities.Operation;
import ust.tad.bashplugin.models.tadm.entities.Property;

public class AnnotatedComponent extends Component{

    private Confidence confidence;


    public AnnotatedComponent() {
        super();
    }

    public AnnotatedComponent(String name, String description, List<Property> properties, List<Operation> operations, ComponentType type, List<Artifact> artifacts, Confidence confidence) {
        super(name, description, properties, operations, type, artifacts);
        this.confidence = confidence;
    }

    public Confidence getConfidence() {
        return this.confidence;
    }

    public void setConfidence(Confidence confidence) {
        this.confidence = confidence;
    }

    public AnnotatedComponent confidence(Confidence confidence) {
        setConfidence(confidence);
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof AnnotatedComponent)) {
            return false;
        }
        AnnotatedComponent annotatedComponent = (AnnotatedComponent) o;
        return Objects.equals(getId(), annotatedComponent.getId()) 
            && Objects.equals(getName(), annotatedComponent.getName()) 
            && Objects.equals(getDescription(), annotatedComponent.getDescription()) 
            && Objects.equals(getProperties(), annotatedComponent.getProperties()) 
            && Objects.equals(getOperations(), annotatedComponent.getOperations())
            && Objects.equals(getType(), annotatedComponent.getType())
            && Objects.equals(getArtifacts(), annotatedComponent.getArtifacts())
            && Objects.equals(confidence, annotatedComponent.confidence);
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getName(), getDescription(), getProperties(), getOperations(), getType(), getArtifacts(), confidence);
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
            ", confidence='" + getConfidence() + "'" +
            "}";
    }

    
}
