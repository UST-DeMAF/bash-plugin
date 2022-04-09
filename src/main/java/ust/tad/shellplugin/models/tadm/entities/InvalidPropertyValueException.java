package ust.tad.shellplugin.models.tadm.entities;

public class InvalidPropertyValueException extends Exception{
    public InvalidPropertyValueException(String errorMessage) {
        super(errorMessage);
    }    
}
