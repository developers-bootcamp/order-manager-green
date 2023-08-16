package com.sap.ordermanagergreen.service;

import com.sap.ordermanagergreen.model.ProductCategory;
import com.sap.ordermanagergreen.repository.IProductCategoryRepository;
import com.sap.ordermanagergreen.resolver.ProductCategoryResolver;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith({MockitoExtension.class})
public class ProductCategoryServiceTest {

//    @InjectMocks
//    private ProductCategoryService productCategoryService;

    @Mock
    private IProductCategoryRepository productCategoryRepository;

    @Test
    public void insertProductCategory_whenItExists_throw_Exeption(ProductCategory productCategory){
       // Mockito.when(this.productCategoryRepository.existsByName(productCategory.getName())).thenReturn(true);

    }
}