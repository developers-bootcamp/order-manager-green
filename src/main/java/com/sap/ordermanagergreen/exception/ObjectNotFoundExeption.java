package com.sap.ordermanagergreen.exception;

public class ObjectNotFoundExeption extends RuntimeException{
    public ObjectNotFoundExeption(String errorMessage){
        super(errorMessage);
    }
}
