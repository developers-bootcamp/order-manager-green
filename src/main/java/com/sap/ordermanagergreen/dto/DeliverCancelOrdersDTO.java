package com.sap.ordermanagergreen.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.Month;
import java.time.Year;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DeliverCancelOrdersDTO {
    private int month;
    private int year;
    private int cancelled;
    private int delivered;
}


