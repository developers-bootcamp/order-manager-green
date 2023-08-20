package com.sap.ordermanagergreen.model;

import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.data.mongodb.core.mapping.DBRef;

@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class OrderPayment {

    @DBRef
    private Order order;
    @DBRef
    private User user;
    private int amount;
    private long invoiceNumber;
    private PaymentType paymentType;
    private AuditData auditData;

}