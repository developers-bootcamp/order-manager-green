package com.sap.ordermanegergreen.exception;

public class ObjectExistException extends RuntimeException {
    public ObjectExistException(String message){
        super(message);
    }
}
