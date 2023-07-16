package com.sap.ordermanegergreen.exception;

public class NotValidException extends Exception {

    public NotValidException(String type) {
        super(type + " is not valid");
    }

}
