package com.sap.ordermanegergreen.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "Roles")  // לשנות שם לטבלה
public class Roles {
    
    @Id
    private String id;
    
    private AvailableRoles name;
    
    private String desc;
    
    private AuditDate auditData;
    
}
