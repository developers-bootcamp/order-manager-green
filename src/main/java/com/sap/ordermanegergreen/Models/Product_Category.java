package com.sap.ordermanegergreen.Models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "Product_Category")
public class Product_Category {
    @Id private long Id;
    private String Name;
    private String Desc;
    private Company Company_Id;
    @DBRef
    private List<AuditData> Audit_Data;
}
