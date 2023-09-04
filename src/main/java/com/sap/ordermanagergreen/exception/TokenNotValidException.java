package com.sap.ordermanagergreen.exception;

public class TokenNotValidException extends  Exception{
    public TokenNotValidException(String type) {
        super(type + " token not valid");
    }
}
