package com.sap.ordermanagergreen.dto;

import com.sap.ordermanagergreen.model.Month;
import com.sap.ordermanagergreen.model.Product;

import java.util.List;

public class TopProductDTO {
    Month month;
    List<ProductCountDto> countForProduct;

    public TopProductDTO(Month month,List<ProductCountDto> countForProduct){
        this.countForProduct = countForProduct;
        this.month = month;
    }

}
