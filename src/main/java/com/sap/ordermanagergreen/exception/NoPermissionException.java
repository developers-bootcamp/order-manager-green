package com.sap.ordermanagergreen.exception;

public class NoPermissionException extends RuntimeException{
    public NoPermissionException(String message) {
        super(message);
    }
}
