package com.sap.ordermanagergreen.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.Month;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DeliverCancelOrdersDTO {
    private Month month;
    private int cancelled;
    private int delivered;
}


