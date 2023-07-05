package com.sap.ordermanegergreen.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
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
