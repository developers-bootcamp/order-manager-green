package com.sap.ordermanegergreen.Models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.DBRef;

import java.util.List;

enum Payment_Types{CREDIT,DEBIT};
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Order_Payment {
   private Orders Order_Id;
    private User User;
    private int Amount;
    private long Invoice_number;
    private Payment_Types Payment_Type;
    @DBRef
    private AuditData Audit_Data;
}
