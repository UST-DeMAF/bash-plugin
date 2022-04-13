package ust.tad.bashplugin.models.tadm.entities;

import java.net.URI;
import java.util.Objects;

public class Artifact {

    private String name;
    
    private String type;

    private URI fileUri;
    

    public Artifact() {
    }

    public Artifact(String name, String type, URI fileUri) {
        this.name = name;
        this.type = type;
        this.fileUri = fileUri;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public URI getFileUri() {
        return this.fileUri;
    }

    public void setFileUri(URI fileUri) {
        this.fileUri = fileUri;
    }

    public Artifact name(String name) {
        setName(name);
        return this;
    }

    public Artifact type(String type) {
        setType(type);
        return this;
    }

    public Artifact fileUri(URI fileUri) {
        setFileUri(fileUri);
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof Artifact)) {
            return false;
        }
        Artifact artifact = (Artifact) o;
        return Objects.equals(name, artifact.name) && Objects.equals(type, artifact.type) && Objects.equals(fileUri, artifact.fileUri);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, type, fileUri);
    }

    @Override
    public String toString() {
        return "{" +
            " name='" + getName() + "'" +
            ", type='" + getType() + "'" +
            ", fileUri='" + getFileUri() + "'" +
            "}";
    }

}
