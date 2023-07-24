package com.sap.ordermanagergreen.model;

import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "Users")
@SuperBuilder
public class User {

    @Id
    @Generated private String id;

    private String fullName;

    private String password;

    private Address address;
  
    @DBRef
    private Role roleId;

    @DBRef
    private Company companyId;

    private AuditData auditData;

}
