package com.sap.ordermanegergreen.controller;
import com.sap.ordermanegergreen.model.ProductCategory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import com.sap.ordermanegergreen.service.ProductCategoryService;

import java.util.List;

@RestController
@RequestMapping("/productCategory")
public class ProductCategoryController {
    private ProductCategoryService productCategoryService;
    @Autowired
    public ProductCategoryController(ProductCategoryService productCategoryService){
        this.productCategoryService=productCategoryService;
    }

    @GetMapping
    @RequestMapping("/getAll")
    public ResponseEntity<List<ProductCategory>> getAll() {
        return productCategoryService.getAllCategories();
    }
    @PostMapping
    @RequestMapping("/add")
    public ResponseEntity<String> add(@RequestBody ProductCategory productCategory){
        System.out.println("💕💕 in createProductCategory");
        return productCategoryService.saveProductCategory(productCategory);

    }
    @DeleteMapping
    @RequestMapping("")
    public ResponseEntity<String> deleteById(@PathVariable("id") String id){
        return  productCategoryService.deleteProductCategory(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> editProductCategory(@PathVariable("id") String id, @RequestBody ProductCategory productCategory){
        return productCategoryService.editProductCategory(id,productCategory);
    }
}