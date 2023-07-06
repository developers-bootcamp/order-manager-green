package com.sap.ordermanegergreen.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "Company")
public class Company {
    @Id private String id;
    private String name;
    private String currency;
    private AuditData auditData;
    public Company(Company company){
        this.id=company.id;
        this.name=company.name;
        this.currency=company.currency;
        this.auditData=company.auditData;

    }
}
