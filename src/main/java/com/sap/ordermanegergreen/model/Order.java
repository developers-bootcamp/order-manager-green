package com.sap.ordermanegergreen.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@Document(collection = "Orders")
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder(toBuilder = true)
//@ToString
public class Order {
 @Id private String id;
   @DBRef
   private User employeeId ;
    @DBRef
    private User customerId;
    private double totalAmount;
    private List<OrderItem> orderItemsList;
    private String orderStatusId;
    @DBRef
    private Company companyId;
    private String creditCardNumber;
    private String  expiryOn;
    private String cvc;
    private Boolean notificationFlag;
    private AuditDate auditDate;
}
