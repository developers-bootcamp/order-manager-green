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
    @DBRef
    private Address Addresses;
    private Roles Role;
    private Company Company;
    @DBRef
    private AuditData AuditData;
}
