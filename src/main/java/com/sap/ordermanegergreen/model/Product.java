package com.sap.ordermanegergreen.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

;
@Data
@NoArgsConstructor
@AllArgsConstructor
//@Collation
@Document(collection = "Product")
@SuperBuilder(toBuilder = true)
public class Product {
    @Id private String id;
    private String name;
    private String description;
    private  double price;
    private int discount;
    private DiscountTypes discountType;
    @DBRef
    private ProductCategory categoryId;
    private int inventory;
    @DBRef
    private Company companyId;
    private AuditDate auditDate;
}
