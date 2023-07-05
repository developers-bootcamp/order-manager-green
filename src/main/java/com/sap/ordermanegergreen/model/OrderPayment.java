package com.sap.ordermanegergreen.model;

import lombok.*;
import org.springframework.data.mongodb.core.mapping.DBRef;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderPayment {

    @DBRef
    private String orderId;
    
    @DBRef
    private String userId;

    private int amount;

    private long invoiceNumber;

    private PaymentTypes paymentType;

    private AuditDate auditDate;
    
}
