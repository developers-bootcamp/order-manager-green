package com.sap.ordermanegergreen.Models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.DBRef;

import java.util.List;

enum Payment_Types{Credit,debit};
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Order_Payment {
   private long Order_Id;
    private long User;
    private int Amount;
    private long Invoice_number;
    private Payment_Types Payment_Type;
    @DBRef
    private List<AuditData> Audit_Data;
}
