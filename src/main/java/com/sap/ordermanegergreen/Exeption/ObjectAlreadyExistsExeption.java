package com.sap.ordermanegergreen.Exeption;

public class ObjectAlreadyExistsExeption extends RuntimeException{
    public ObjectAlreadyExistsExeption(String errorMessage) {
        super(errorMessage);
    }
}
