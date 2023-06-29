package com.sap.ordermanegergreen.Models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

;
//@Document לשנות שם לטבלה
@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "Roles")
public class Roles {
    @Id
    private String Id;
    private AvailableRoles Name;
    private String Desc;
    private AuditData AuditData;

}
