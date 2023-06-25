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
 @Id private String Id;
   private User Employee_Id ;
    private User Customer_Id;
    private double Total_amount;
   @DBRef
    private List<Order_Items> Order_Items_List;
    private String Order_Status_Id;
    private Company Company_Id;
    private String CreditCardNumber;
    private String  ExpiryOn;
    private String Cvc;
    private Boolean NotificationFlag;
   @DBRef
    private AuditData Audit_Data;
}
