package ust.tad.shellplugin.models.tadm.entities;

import java.util.List;

public abstract class ModelElementType extends ModelEntity{

    protected ModelElementType() {
        super();
    }

    protected ModelElementType(String name, String description, List<Property> properties, List<Operation> operations) {
        super(name, description, properties, operations);
    }

}
