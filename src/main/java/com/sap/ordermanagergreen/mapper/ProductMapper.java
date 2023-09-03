package com.sap.ordermanagergreen.mapper;

import com.sap.ordermanagergreen.dto.ProductDTO;
import com.sap.ordermanagergreen.dto.ProductNameDTO;
import com.sap.ordermanagergreen.model.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    ProductMapper INSTANCE = Mappers.getMapper(ProductMapper.class);
    @Mapping(source = "category.name", target = "productCategoryName")
    ProductDTO productToDto(Product p);
    @Mapping(source = "productCategoryName", target = "category.name")
    Product dtoToProduct(ProductDTO p);
    List<ProductDTO> productToDto(List<Product> list);
    List<Product> dtoToProduct(List<ProductDTO> list);

}
