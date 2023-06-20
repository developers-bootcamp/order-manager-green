package com.sap.ordermanegergreen.Models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

enum Available_Roles{Admin, employee, customer};
//@Document לשנות שם לטבלה
@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "Roles")
public class Roles {
    @Id private long Id;
    private Available_Roles Name;
    private String Desc;
    @DBRef
    private List<AuditData> Audit_Data;

}
