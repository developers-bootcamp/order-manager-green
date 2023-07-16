package com.sap.ordermanegergreen.exception;

public class ConflictException extends Exception{
public ConflictException(String massage){
    super(massage);
}
    public String getMessage() {
        return super.getMessage();
    }

}

