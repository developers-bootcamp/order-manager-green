package com.sap.ordermanegergreen.exception;

public class ObjectDoesNotExsistException extends RuntimeException{
    public ObjectDoesNotExsistException(String message){
        super(message);
    }
}
