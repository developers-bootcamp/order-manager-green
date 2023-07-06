package com.sap.ordermanegergreen.controllers;
import com.sap.ordermanegergreen.models.ProductCategory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import com.sap.ordermanegergreen.services.ProductCategoryService;

import java.util.List;

@Controller
@RestController
@RequestMapping("/ProductCategory")
public class ProductCategoryController {
    ProductCategoryService ProductCategoryService;
    @Autowired
    public ProductCategoryController(ProductCategoryService ProductCategoryService){
        this.ProductCategoryService=ProductCategoryService;
    }

    @GetMapping
    public ResponseEntity<List<ProductCategory>> getAll() {
        return ProductCategoryService.getAllCategories();
    }
    @PostMapping
    public ResponseEntity<String> add(@RequestBody ProductCategory productCategory){
        System.out.println("💕💕 in createProductCategory");
        return ProductCategoryService.saveProductCategory(productCategory);

    }
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteById(@PathVariable("id") String id){
        return  ProductCategoryService.deleteProductCategory(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> editProductCategory(@PathVariable("id") String id, @RequestBody ProductCategory productCategory){
        return ProductCategoryService.editProductCategory(id,productCategory);
    }
}

