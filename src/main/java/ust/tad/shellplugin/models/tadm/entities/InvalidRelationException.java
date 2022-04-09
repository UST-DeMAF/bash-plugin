package ust.tad.shellplugin.models.tadm.entities;

public class InvalidRelationException extends Exception{
    public InvalidRelationException(String errorMessage) {
        super(errorMessage);
    }
}
