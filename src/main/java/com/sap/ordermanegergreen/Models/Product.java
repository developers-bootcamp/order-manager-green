package com.sap.ordermanegergreen.Models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

enum Discount_Types {Percentage,Fixed_Amount} ;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "Product")
public class Product {
    @Id private long Id;
    private String Name;
    private String Desc;
    private  int Price;
    private int Discount;
    private Discount_Types Discount_Type;
    private long Category_id;
    private int Inventory;
    //amuont in stock
    private long Company_Id;
    @DBRef
    private List<AuditData> Audit_Data;




}
