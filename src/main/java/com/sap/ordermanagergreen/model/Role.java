package com.sap.ordermanagergreen.model;

import com.sap.ordermanagergreen.model.AuditData;
import com.sap.ordermanagergreen.model.AvailableRoles;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "Role")
public class Role {
    @Id
    private String id;
    private AvailableRoles name;
    private String desc;
    private AuditData auditData;

    public Role(String id) {
        this.id = id;
    }
}
