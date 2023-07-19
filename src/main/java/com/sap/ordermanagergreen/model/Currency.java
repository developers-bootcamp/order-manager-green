package com.sap.ordermanagergreen.model;

public enum Currency {
        EURO("EUR"), SHEKEL("ILS"), FRANC("CHF"), RUBLE("RUB"), DOLLAR("USD");

        private String code;

        private Currency(String code) {
            this.code = code;
        }


}
