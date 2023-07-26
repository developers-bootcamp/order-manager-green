package com.sap.ordermanagergreen.exception;

public class ObjectAlreadyExistsExeption extends RuntimeException{
    public ObjectAlreadyExistsExeption(String errorMessage) {
        super(errorMessage);
    }
}
