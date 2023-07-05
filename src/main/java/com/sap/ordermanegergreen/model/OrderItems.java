package com.sap.ordermanegergreen.model;

import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderItems {
    
    private String productId;
    //כמה עולה ביחד
    private double amount;
    //  (כמות)
    private double quantity ;
    
}
