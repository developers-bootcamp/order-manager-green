package com.sap.ordermanagergreen.dto;

public class ProductCountDto {
    String productName;
    int count;


    public ProductCountDto(String productName, int count) {
        this.productName = productName;
        this.count = count;
    }
}
