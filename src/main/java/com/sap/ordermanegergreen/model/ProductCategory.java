package com.sap.ordermanegergreen.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection ="ProductCategory")
@SuperBuilder(toBuilder = true)
public class ProductCategory {
    @Id
    private String id;
    private String name;
    private String desc;
 @DBRef
    private Company companyId;
    private AuditDate auditDate;

//    public ProductCategory(String id) {Id=id;}
}
