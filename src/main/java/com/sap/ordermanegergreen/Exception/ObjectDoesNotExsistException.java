package com.sap.ordermanegergreen.Exception;

public class ObjectDoesNotExsistException extends RuntimeException{
    public ObjectDoesNotExsistException(String message){
        super(message);
    }
}
