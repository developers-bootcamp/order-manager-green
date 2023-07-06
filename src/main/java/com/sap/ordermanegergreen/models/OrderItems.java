package com.sap.ordermanegergreen.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderItems {
    private Product productId;
    private double amount;
    private double quantity ;

}
