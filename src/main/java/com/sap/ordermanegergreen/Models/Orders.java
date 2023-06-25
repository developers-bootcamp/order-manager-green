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
   private User EmployeeId ;
    private User CustomerId;
    private double TotalAmount;
   @DBRef
    private List<OrderItems> OrderItemsList;
    private String OrderStatusId;
    private Company CompanyId;
    private String CreditCardNumber;
    private String  ExpiryOn;
    private String Cvc;
    private Boolean NotificationFlag;
   @DBRef
    private AuditData AuditData;
}
