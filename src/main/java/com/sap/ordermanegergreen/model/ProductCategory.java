package com.sap.ordermanegergreen.model;


import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
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
    
    @DBRef
    private String companyId;
    
    private AuditDate auditDate;
    
}
