package com.sap.ordermanegergreen.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection ="ProductCategory")
public class ProductCategory {
    @Id
    private String id;
    private String name;
    private String desc;
 // @DBRef("Company")
    private String companyId;
    private AuditDate auditDate;

//    public ProductCategory(String id) {Id=id;}
}
