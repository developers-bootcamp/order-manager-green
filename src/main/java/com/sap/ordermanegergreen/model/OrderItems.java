package com.sap.ordermanegergreen.model;

import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderItems {
    private String productId;
    private double amount;
    //כמה עולה ביחד
    private double quantity ;
    //  (כמות)
}
