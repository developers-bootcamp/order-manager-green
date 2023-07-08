package com.sap.ordermanagergreen.controller;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.sap.ordermanagergreen.model.ProductCategory;
import org.springframework.beans.factory.annotation.Autowired;
import com.sap.ordermanagergreen.service.ProductCategoryService;

import java.util.List;

@CrossOrigin("http://localhost:3000")
@RestController
@RequestMapping("/productCategory")
public class ProductCategoryController {
    
    private ProductCategoryService productCategoryService;
    
    @Autowired
    public ProductCategoryController(ProductCategoryService productCategoryService){
        this.productCategoryService=productCategoryService;
    }

    @GetMapping
    public ResponseEntity<List<ProductCategory>> getAll() {
        return productCategoryService.getAllCategories();
    }
    
    @PostMapping
    public ResponseEntity<String> add(@RequestBody ProductCategory productCategory){
        System.out.println("💕💕 in createProductCategory");
        return productCategoryService.saveProductCategory(productCategory);

    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteById(@PathVariable String id){
        return  productCategoryService.deleteProductCategory(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> put(@PathVariable String id, @RequestBody ProductCategory productCategory){
        return productCategoryService.editProductCategory(id,productCategory);
    }
    
}
