package com.sap.ordermanagergreen.exception;

public class ConflictException extends Exception{
public ConflictException(String massage){
    super(massage);
}
    public String getMessage() {
        return super.getMessage();
    }

}

