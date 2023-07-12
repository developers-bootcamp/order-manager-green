package com.sap.ordermanagergreen.exception;

public class ObjectAlreadyExistsException extends Exception {

    public ObjectAlreadyExistsException(String type) {
        super(type + " already exists");
    }

}
