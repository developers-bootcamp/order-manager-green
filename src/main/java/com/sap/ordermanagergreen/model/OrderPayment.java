package com.sap.ordermanagergreen.model;

import lombok.*;
import org.springframework.data.mongodb.core.mapping.DBRef;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderPayment {

    @DBRef
    private Order orderId;
    
    @DBRef
    private User userId;

    private int amount;

    private long invoiceNumber;

    private PaymentTypes paymentType;

    private AuditData auditData;
    
}
