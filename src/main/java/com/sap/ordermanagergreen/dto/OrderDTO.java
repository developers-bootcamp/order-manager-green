package com.sap.ordermanagergreen.dto;

import com.sap.ordermanagergreen.model.OrderStatus;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import java.io.Serializable;
import java.util.UUID;
@Data
@NoArgsConstructor
 public class OrderDTO implements Serializable {

        @Id
        private String id;
        @Id
        private String customerId;
        private double paymentAmount;
        private OrderStatus orderStatus;
        private Boolean notificationFlag;
        private String creditCardNumber;
        private String expiryOn;
        private String cvc;

}
