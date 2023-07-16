package com.sap.ordermanegergreen.exception;

public class ObjectAlreadyExistsException extends Exception {

    public ObjectAlreadyExistsException(String type) {
        super(type + " already exists");
    }

}
