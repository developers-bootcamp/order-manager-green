package com.sap.ordermanegergreen.Exeption;

public class ObjectNotFoundExeption extends RuntimeException{
    public ObjectNotFoundExeption(String errorMessage){
        super(errorMessage);
    }
}
