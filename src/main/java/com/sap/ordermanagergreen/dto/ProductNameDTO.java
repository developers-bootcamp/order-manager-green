package com.sap.ordermanagergreen.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductNameDTO {
    @Id private String id;
    private String name;

}
