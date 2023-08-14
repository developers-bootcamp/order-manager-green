package com.sap.ordermanagergreen.mapper;

import com.sap.ordermanagergreen.dto.ProductCategoryDTO;
import com.sap.ordermanagergreen.model.ProductCategory;
import org.mapstruct.Mapper;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface ProductCategoryMapper {



    ProductCategoryDTO toDto(ProductCategory productCategory);
    ProductCategory fromDto(ProductCategoryDTO productCategoryDTO);


    default List<ProductCategory> fromDtoList(List<ProductCategoryDTO> productCategoryDTOs) {
        return productCategoryDTOs.stream()
                .map(this::fromDto)
                .collect(Collectors.toList());
    }
    default List<ProductCategoryDTO> toDtoList(List<ProductCategory> productCategories) {
        return productCategories.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }
}

