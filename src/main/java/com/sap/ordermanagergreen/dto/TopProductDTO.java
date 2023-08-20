package com.sap.ordermanagergreen.dto;

import com.sap.ordermanagergreen.model.MonthInYear;
import com.sap.ordermanagergreen.model.Product;

import java.util.List;

public class TopProductDTO {
    MonthInYear month;
    List<ProductCountDto> countForProduct;

    public TopProductDTO(MonthInYear month,List<ProductCountDto> countForProduct){
        this.countForProduct = countForProduct;
        this.month = month;
    }

}
