package com.sap.ordermanegergreen.Models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Order_Items {
    private Product Product_Id;
    private double Amount;
    //כמה עולה ביחד
    private double Quantity ;
    //  (כמות)

}
