package com.sap.ordermanagergreen.dto;

import com.sap.ordermanagergreen.model.Order;
import com.sap.ordermanagergreen.model.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderDTO {
    private String orderId;
    private String customerId;
    private double paymentAmount;
    private OrderStatus status;
    private boolean isCredit;
    private String creditCardNumber;
    private Date expiryDate;
    private String ccv;

}
