package com.sap.ordermanagergreen.model;

public enum Currency {
   // DOLLAR,FRANC,EURO,SHEKEL,RUBLE}
    DOLLAR("USD"), FRANC("CHF"), EURO("EUR"), SHEKEL("ILS"), RUBLE("RUB");
    private final String code;

    Currency(String code) {
        this.code = code;
    }

}
