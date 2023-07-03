package com.sap.ordermanegergreen.model;

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
 @Id private String id;
   @DBRef
   private String employeeId ;
    @DBRef
    private String customerId;
    private double totalAmount;
    private List<OrderItems> orderItemsList;
    private String orderStatusId;
    @DBRef
    private String companyId;
    private String creditCardNumber;
    private String  expiryOn;
    private String cvc;
    private Boolean notificationFlag;
    private AuditDate auditDate;
}
