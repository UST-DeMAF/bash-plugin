package ust.tad.bashplugin.models.tadm.entities;

public class InvalidRelationException extends Exception{
    public InvalidRelationException(String errorMessage) {
        super(errorMessage);
    }
}
