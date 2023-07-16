package com.sap.ordermanagergreen.model;

import lombok.*;
import org.springframework.data.mongodb.core.mapping.DBRef;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderItems {
    @DBRef
    private Product productId;
    //כמה עולה ביחד
    private double amount;
    //  (כמות)
    private double quantity ;
    
}
