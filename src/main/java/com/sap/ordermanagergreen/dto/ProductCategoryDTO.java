package com.sap.ordermanagergreen.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductCategoryDTO {
    @Id
    private String id;
    private String name;
    private String description;

    public ProductCategoryDTO(ProductCategoryDTO productCategoryDTO) {
        id = productCategoryDTO.id;
        name = productCategoryDTO.name;
        description = productCategoryDTO.description;
    }
}
