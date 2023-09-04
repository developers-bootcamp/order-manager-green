package com.sap.ordermanagergreen.dto;

import org.springframework.data.annotation.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductNameDTO {

    @Id
    private String id;
    private String name;
}
