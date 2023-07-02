package com.sap.ordermanegergreen.Controllers;
import com.sap.ordermanegergreen.DTO.ProductCategoryDTO;
import com.sap.ordermanegergreen.DTO.ProductCategoryMapper;
import com.sap.ordermanegergreen.Models.ProductCategory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import com.sap.ordermanegergreen.Services.ProductCategoryService;

import java.util.List;

@Controller
@RestController
@RequestMapping("/ProductCategory")
public class ProductCategoryController {
    private final ProductCategoryService ProductCategoryService;
    private final ProductCategoryMapper productCategoryMapper;

    @Autowired
    public ProductCategoryController(ProductCategoryService productCategoryService, ProductCategoryMapper productCategoryMapper) {
        this.ProductCategoryService = productCategoryService;
        this.productCategoryMapper = productCategoryMapper;
    }



    @GetMapping("/")
    public ResponseEntity<List<ProductCategoryDTO>> getAll() {
        return ProductCategoryService.getAllCategories();
    }

    @PostMapping("/")
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

