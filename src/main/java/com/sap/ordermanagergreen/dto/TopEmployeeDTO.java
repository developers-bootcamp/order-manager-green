package com.sap.ordermanagergreen.dto;

import com.sap.ordermanagergreen.model.User;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class TopEmployeeDTO {

     private User user;
     private  int countOfDeliveredOrders;
}
