package com.sap.ordermanagergreen.exception;

public class ObjectNotExistException extends Exception {
    public ObjectNotExistException(String type) {
        super(type + " not exists");
    }

}
