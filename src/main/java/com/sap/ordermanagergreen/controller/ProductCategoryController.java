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

    @Autowired
    private ProductCategoryService productCategoryService;

    @GetMapping
    public ResponseEntity<List<ProductCategory>> get() {
        return productCategoryService.get();
    }

    @PostMapping
    public ResponseEntity<String> add(@RequestBody ProductCategory productCategory) {
        return productCategoryService.add(productCategory);
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> update(@PathVariable String id, @RequestBody ProductCategory productCategory) {
        return productCategoryService.update(id, productCategory);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable String id) {
        return productCategoryService.delete(id);
    }

}