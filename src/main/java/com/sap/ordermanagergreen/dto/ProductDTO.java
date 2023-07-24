package com.sap.ordermanagergreen.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import org.springframework.data.annotation.Id;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductDTO {
    @Id private   String id;
    private String name;
    private String description;
    private String categoryId;
    private String inventory;
    private double price;
}