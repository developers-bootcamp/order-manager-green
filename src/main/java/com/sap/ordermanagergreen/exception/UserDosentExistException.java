package com.sap.ordermanagergreen.exception;

public class UserDosentExistException extends  Exception{
    public UserDosentExistException(String massage){
        super(massage);
    }
    public String getMessage() {
        return super.getMessage();
    }
}
