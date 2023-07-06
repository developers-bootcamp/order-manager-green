package com.sap.ordermanegergreen.models;

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
@Document(collection = "Product")
public class Product {
    @Id
    private String id;
    private String name;
    private String description;
    private  double price;
    private int discount;
    private DiscountTypes discountType;
    private ProductCategory categoryId;
    private int inventory;
    @DBRef
    private Company company;
    private AuditData auditData;
    @Override
    public String toString(){
        return auditData.getCreateDate().toString();
    }
}
