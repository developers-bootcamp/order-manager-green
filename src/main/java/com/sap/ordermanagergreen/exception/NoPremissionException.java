package com.sap.ordermanagergreen.exception;

public class NoPremissionException extends Exception {
    public NoPremissionException(String type) {
        super(type + " no permission");
    }
}