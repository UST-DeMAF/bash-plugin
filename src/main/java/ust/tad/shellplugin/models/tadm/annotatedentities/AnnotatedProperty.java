package ust.tad.shellplugin.models.tadm.annotatedentities;

import java.util.Objects;

import ust.tad.shellplugin.models.tadm.entities.InvalidPropertyValueException;
import ust.tad.shellplugin.models.tadm.entities.Property;
import ust.tad.shellplugin.models.tadm.entities.PropertyType;

public class AnnotatedProperty extends Property{

    private Confidence confidence;

    public AnnotatedProperty() {
        super();
    }

    public AnnotatedProperty(String key, PropertyType type, boolean required, Object value, Confidence confidence) throws InvalidPropertyValueException {
        super(key, type, required, value);
        this.confidence = confidence;
    }

    public Confidence getConfidence() {
        return this.confidence;
    }

    public void setConfidence(Confidence confidence) {
        this.confidence = confidence;
    }

    public AnnotatedProperty confidence(Confidence confidence) {
        setConfidence(confidence);
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof AnnotatedProperty)) {
            return false;
        }
        AnnotatedProperty annotatedProperty = (AnnotatedProperty) o;
        return Objects.equals(getKey(), annotatedProperty.getKey()) 
            && Objects.equals(getType(), annotatedProperty.getType()) 
            && isRequired() == annotatedProperty.isRequired() 
            && Objects.equals(getValue(), annotatedProperty.getValue()) 
            && Objects.equals(confidence, annotatedProperty.confidence);
    }

    @Override
    public int hashCode() {
        return Objects.hash(getKey(), getType(), isRequired(), getValue(), confidence);
    }

    @Override
    public String toString() {
        return "{" +
            " key='" + getKey() + "'" +
            ", type='" + getType() + "'" +
            ", required='" + isRequired() + "'" +
            ", value='" + getValue() + "'" +
            ", confidence='" + getConfidence() + "'" +
            "}";
    }

    
}
