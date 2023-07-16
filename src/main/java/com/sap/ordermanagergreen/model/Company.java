package com.sap.ordermanagergreen.model;

import lombok.*;
import org.springframework.data.annotation.Id;
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
    
    private AuditData auditData;

    public Company(String name) {
        this.id=name;
    }
}
