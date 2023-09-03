package com.sap.ordermanagergreen.resolver.service;//package com.sap.ordermanagergreen.resolver.service;


import com.sap.ordermanagergreen.controller.ProductCategoryController;
import com.sap.ordermanagergreen.dto.ProductCategoryDTO;
import com.sap.ordermanagergreen.exception.ObjectExistException;
import com.sap.ordermanagergreen.exception.ObjectNotExistException;
import com.sap.ordermanagergreen.model.ProductCategory;
import com.sap.ordermanagergreen.service.ProductCategoryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Collections;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;


public class ProductCategoryTest {

    @Mock
    private ProductCategoryService productCategoryService;

    @InjectMocks
    private ProductCategoryController productCategoryController;

    private MockMvc mockMvc;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(productCategoryController).build();
    }

    @Test
    public void testGetProductCategories() throws Exception {
        when(productCategoryService.get(anyString())).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/productCategory")
                        .header("Authorization", "mockedToken"))
                .andExpect(status().isOk())
                .andExpect(content().json("[]"));
    }

    @Test
    public void testAddProductCategory() throws Exception {
        doNothing().when(productCategoryService).add(anyString(), any(ProductCategory.class));

        mockMvc.perform(post("/productCategory")
                        .header("Authorization", "mockedToken")
                        .content("{}")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("success: true"));
    }


    @Test
    public void testDeleteProductCategory() throws Exception {
        doNothing().when(productCategoryService).delete(anyString(), anyString());

        mockMvc.perform(delete("/productCategory/{id}", "1")
                        .header("Authorization", "mockedToken"))
                .andExpect(status().isOk())
                .andExpect(content().string("{\"success\": true}"));
    }

    @Test
    public void testUpdateProductCategory() throws Exception {
        doNothing().when(productCategoryService).update(anyString(), any(ProductCategory.class), anyString());

        mockMvc.perform(put("/productCategory/{id}", "1")
                        .header("Authorization", "mockedToken")
                        .content("{}")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("success: true"));
    }
}
