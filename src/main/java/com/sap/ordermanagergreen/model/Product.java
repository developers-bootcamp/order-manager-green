package com.sap.ordermanagergreen.model;

import lombok.*;
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
        
    @DBRef
    private String categoryId;
    
    private int inventory;
    
    @DBRef
    private String companyId;
    
    private AuditData auditData;
    
}
