package com.sap.ordermanagergreen.exception;

public class CompanyNotExistException extends  Exception{
    public CompanyNotExistException(String massage){
        super(massage);
    }
    public String getMessage() {
        return super.getMessage();
    }
}
