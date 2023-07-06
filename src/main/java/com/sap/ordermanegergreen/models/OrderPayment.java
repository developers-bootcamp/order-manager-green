package com.sap.ordermanegergreen.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.DBRef;

;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderPayment {

   private Order orderId;

   private User user;

    private int amount;

    private long invoiceNumber;

    private PaymentTypes paymentType;
    @DBRef
    private AuditData auditData;
}
