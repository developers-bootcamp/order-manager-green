package com.sap.ordermanagergreen.model;

import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.data.mongodb.core.mapping.DBRef;

@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class OrderItem {

    @DBRef
    private Product product;
    private double amount;
    private int quantity;

}