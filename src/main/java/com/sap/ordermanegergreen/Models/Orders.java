package com.sap.ordermanegergreen.Models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@Document(collection = "Orders")
@NoArgsConstructor
@AllArgsConstructor
//@ToString
public class Orders {
 @Id private long Id;
   private long Employee_Id ;
    private long Customer_Id;
    private int Total_amount;
   @DBRef
    private List<Order_Items> Order_Items_List;
    private long Order_Status_Id;
    private long Company_Id;
    private String CreditCardNumber;
    private String  ExpiryOn;
    private String Cvc;
    private boolean NotificationFlag;
   @DBRef
    private List<AuditData> Audit_Data;


}
