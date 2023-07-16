package com.sap.ordermanegergreen.model;

import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "Users")
@SuperBuilder(toBuilder = true)
public class User {
    @Id
    private String id;
    private String fullName;
    private String password;
    private Address address;
    @DBRef
    private Role roleId;
    @DBRef
    private Company companyId;
    private AuditDate auditDate;
}
