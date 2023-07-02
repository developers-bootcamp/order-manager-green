package com.sap.ordermanegergreen.Models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "ProductCategory")
public class ProductCategory {
    @Id
    private String Id;
    private String Name;
    private String Desc;
    @DBRef
    private Company CompanyId;
    private AuditData AuditData;

//    public ProductCategory(String id) {Id=id;}
}