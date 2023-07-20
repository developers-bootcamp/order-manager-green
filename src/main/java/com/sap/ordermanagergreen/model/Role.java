package com.sap.ordermanagergreen.model;

import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Document(collection = "Roles")  // לשנות שם לטבלה
public class Role {

    @Id
    private String id;
    private AvailableRole name;
    private String description;
    private AuditData auditData;
}
