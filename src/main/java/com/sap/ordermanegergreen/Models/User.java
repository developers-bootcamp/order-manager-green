package com.sap.ordermanegergreen.Models;

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
    private String Id;
    private String FullName;
    private String Password;
    private Address AddressId;
    @DBRef
    private Roles RoleId;
    @DBRef
    private Company CompanyId;
    private AuditData AuditData;
}
