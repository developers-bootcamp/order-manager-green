package com.sap.ordermanagergreen.model;

public enum AvailableRole
{
    ADMIN("ADMIN"),
    EMPLOYEE("EMPLOYEE"),
    CUSTOMER("CUSTOMER");
    private final String name;

    AvailableRole(String name) {
        this.name = name;
    }
    public String getName() {
        return name;
    }
}





