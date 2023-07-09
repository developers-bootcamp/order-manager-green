package com.sap.ordermanegergreen.Exception;


public class ObjectExistException extends RuntimeException{
    public ObjectExistException(String message){
        super(message);
    }
}
