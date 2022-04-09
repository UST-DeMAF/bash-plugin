package ust.tad.shellplugin.models.tadm.annotatedentities;

import java.net.URI;
import java.util.Objects;

import ust.tad.shellplugin.models.tadm.entities.Artifact;

public class AnnotatedArtifact extends Artifact{

    private Confidence confidence;

    public AnnotatedArtifact() {
        super();
    }

    public AnnotatedArtifact(String name, String type, URI fileUri, Confidence confidence) {
        super(name, type, fileUri);
        this.confidence = confidence;
    }

    public Confidence getConfidence() {
        return this.confidence;
    }

    public void setConfidence(Confidence confidence) {
        this.confidence = confidence;
    }

    public AnnotatedArtifact confidence(Confidence confidence) {
        setConfidence(confidence);
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof AnnotatedArtifact)) {
            return false;
        }
        AnnotatedArtifact annotatedArtifact = (AnnotatedArtifact) o;
        return Objects.equals(getName(), annotatedArtifact.getName()) 
            && Objects.equals(getType(), annotatedArtifact.getType()) 
            && Objects.equals(getFileUri(), annotatedArtifact.getFileUri()) 
            && Objects.equals(confidence, annotatedArtifact.confidence);
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName(), getType(), getFileUri(), confidence);
    }

    @Override
    public String toString() {
        return "{" +
            " name='" + getName() + "'" +
            ", type='" + getType() + "'" +
            ", fileUri='" + getFileUri() + "'" +
            ", confidence='" + getConfidence() + "'" +
            "}";
    }
    
}
