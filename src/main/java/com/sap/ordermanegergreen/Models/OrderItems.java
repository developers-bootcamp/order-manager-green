package com.sap.ordermanegergreen.Models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderItems {
    private Product ProductId;
    private double Amount;
    //כמה עולה ביחד
    private double Quantity ;
    //  (כמות)

}
