package com.sap.ordermanagergreen.model;

import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Document(collection = "Users")
public class User {

    @Id
    private String id;
    private String fullName;
    private String password;
    private Address address;
    @DBRef
    private Role role;
    @DBRef
    private Company company;
    private AuditData auditData;

}