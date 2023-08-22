package com.sap.ordermanagergreen.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;
@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Document(collection = "Orders")
public class Order {

    @Id
    private String id;
    @DBRef
    private User employee;
    @DBRef
    private User customer;
    private double totalAmount;
    private List<OrderItem> orderItemsList;
    private OrderStatus orderStatus;
    @DBRef
    private Company company;
    private String creditCardNumber;
    private String expiryOn;
    private String cvc;
    private Boolean notificationFlag;
    private AuditData auditData;

}