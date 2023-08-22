package com.sap.ordermanagergreen.dto;

import com.sap.ordermanagergreen.model.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TopEmployeeDTO {

     private User user;
     private  int CountOfDeliveredOrders;
}
