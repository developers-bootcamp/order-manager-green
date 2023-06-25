package com.sap.ordermanegergreen.Models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

;
//@Document לשנות שם לטבלה
@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "Roles")
public class Roles {
    @Id private String Id;
    private Available_Roles Name;
    private String Desc;
    @DBRef
    private AuditData Audit_Data;

}
