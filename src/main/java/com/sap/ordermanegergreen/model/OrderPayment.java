package com.sap.ordermanegergreen.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.DBRef;

;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderPayment {

    @DBRef
   private String orderId;
    @DBRef
   private String user;

    private int amount;

    private long invoiceNumber;

    private PaymentTypes paymentType;

    private AuditDate auditDate;
}
