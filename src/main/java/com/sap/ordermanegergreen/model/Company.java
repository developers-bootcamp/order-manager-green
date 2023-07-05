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
@Document(collection ="Company")
public class Company {
    
    @Id 
    private String id;
    
    private String name;
    
    private String currency;
    
    private AuditDate auditDate;
    
}
