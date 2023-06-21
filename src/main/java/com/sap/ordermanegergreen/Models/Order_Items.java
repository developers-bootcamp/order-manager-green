package com.sap.ordermanegergreen.Models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Order_Items {
    private long Product_Id;
    private int Amount;
    //כמה עולה ביחד
    private int Quantity ;
    //  (כמות)

}
