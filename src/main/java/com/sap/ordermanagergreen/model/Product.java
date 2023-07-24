package com.sap.ordermanagergreen.model;

import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Document(collection = "Product")
public class Product {
    
    @Id
    private String id;
    
    private String name;
    
    private String description;

    private double price;

    private int discount;

    private DiscountType discountType;

    @DBRef
    private ProductCategory category;

    private int inventory;
    
    @DBRef
    private Company company;
    private AuditData auditData;
    
}
