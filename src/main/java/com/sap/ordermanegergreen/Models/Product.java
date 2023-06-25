package com.sap.ordermanegergreen.Models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.annotation.Collation;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
//@Collation
@Document(collection = "Product")
public class Product {
    @Id private long Id;
    private String Name;
    private String Desc;
    private  double Price;
    private int Discount;
    private Discount_Types Discount_Type;
    private Product_Category Category_id;
    private int Inventory;
    //amuont in stock
    private Company Company_Id;
    @DBRef
    private AuditData Audit_Data;
}
