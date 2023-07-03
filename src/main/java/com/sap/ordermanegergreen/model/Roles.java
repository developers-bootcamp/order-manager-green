package com.sap.ordermanegergreen.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

;
//@Document לשנות שם לטבלה
@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "Roles")
public class Roles {
    @Id
    private String id;
    private AvailableRoles name;
    private String desc;
    private AuditDate auditData;
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
