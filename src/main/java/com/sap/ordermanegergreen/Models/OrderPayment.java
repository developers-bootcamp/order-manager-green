package com.sap.ordermanegergreen.Models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.DBRef;

;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderPayment {

   private Orders OrderId;

   private User User;

    private int Amount;

    private long InvoiceNumber;

    private PaymentTypes PaymentType;
    @DBRef
    private AuditData AuditData;
}
