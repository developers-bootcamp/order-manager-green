package com.sap.ordermanagergreen.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "Orders")
public class Orders {

    @Id
    private String id;

    @DBRef
    private User employeeId;

    @DBRef
    private User customerId;

    private double totalAmount;

    private List<OrderItems> orderItemsList;

    private OrderStatus orderStatus;

    @DBRef
    private String companyId;

    private String creditCardNumber;

    private String expiryOn;

    private String cvc;

    private Boolean notificationFlag;

    private AuditData auditData;

}
