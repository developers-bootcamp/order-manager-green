package com.sap.ordermanegergreen.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "Product")
public class Product {
    
    @Id 
    private String id;
    
    private String name;
    
    private String description;
    
    private  double price;
    
    private int discount;
    
    private DiscountTypes discountType;
    
    private String categoryId;
    
    private int inventory;
    
    @DBRef
    private String companyId;
    
    private AuditDate auditData;
    
}
