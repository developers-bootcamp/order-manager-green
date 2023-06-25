package com.sap.ordermanegergreen.Models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

;
@Data
@NoArgsConstructor
@AllArgsConstructor
//@Collation
@Document(collection = "Product")
public class Product {
    @Id private String Id;
    private String Name;
    private String Desc;
    private  double Price;
    private int Discount;
    private DiscountTypes DiscountType;
    private ProductCategory CategoryId;
    private int Inventory;
    //amuont in stock
    private Company CompanyId;
    @DBRef
    private AuditData AuditData;




}
