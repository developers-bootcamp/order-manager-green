package com.sap.ordermanegergreen.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "Users")
public class User {
    @Id
    private String id;
    private String fullName;
    private String password;
    private Address addressId;
    @DBRef
    private String roleId;
    @DBRef
    private String companyId;
    private AuditDate auditData;
}
