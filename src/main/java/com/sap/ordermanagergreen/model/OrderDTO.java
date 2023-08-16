package com.sap.ordermanagergreen.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.UUID;
@Data
@NoArgsConstructor
 public class OrderDTO implements Serializable {
        private String id = UUID.randomUUID().toString();
        private String customer;
}
