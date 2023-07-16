package com.sap.ordermanegergreen.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.data.mongodb.core.mapping.DBRef;

;
@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder(toBuilder = true)
public class OrderPayment {

    @DBRef
   private Order orderId;
    @DBRef
   private User user;

    private int amount;

    private long invoiceNumber;

    private PaymentTypes paymentType;

    private AuditDate auditDate;
}
