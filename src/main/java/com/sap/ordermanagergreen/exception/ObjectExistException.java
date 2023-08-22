package com.sap.ordermanagergreen.exception;

public class ObjectExistException extends Exception {
    public ObjectExistException(String type) {
        super(type + " already exists");
    }
}