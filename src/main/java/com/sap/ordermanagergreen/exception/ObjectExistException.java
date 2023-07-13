package com.sap.ordermanagergreen.exception;


public class ObjectExistException extends RuntimeException{
    public ObjectExistException(String type) {
        super(type + " already exists");
    }
}
