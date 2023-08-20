package com.sap.ordermanagergreen.exception;

public class NotValidException extends Exception {
    public NotValidException(String type) {
        super(type + " is not valid");
    }
}