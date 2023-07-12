package com.sap.ordermanagergreen.model;

public enum AvailableRoles
{
    ADMIN("ADMIN"),
    EMPLOYEE("EMPLOYEE"),
    CUSTOMER("CUSTOMER");
    private String name;

    AvailableRoles(String name) {
        this.name = name;
    }
    public String getName() {
        return name;
    }
}





