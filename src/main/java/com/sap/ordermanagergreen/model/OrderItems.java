package com.sap.ordermanagergreen.model;

import lombok.*;

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
