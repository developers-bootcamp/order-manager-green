package com.sap.ordermanagergreen.service;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.http.ResponseEntity;
import com.sap.ordermanagergreen.model.ProductCategory;
import org.springframework.beans.factory.annotation.Autowired;
import com.sap.ordermanagergreen.repository.IProductCategoryRepository;

import java.util.List;
import java.util.Optional;

@Service
public class ProductCategoryService {

    IProductCategoryRepository productCategoryRepository;

    @Autowired
    public ProductCategoryService(IProductCategoryRepository productCategoryRepository) {
        this.productCategoryRepository = productCategoryRepository;
    }

    public ResponseEntity<String> saveProductCategory(ProductCategory productCategory) {
        String categoryName = productCategory.getName();
        if (doesCategoryExist(categoryName) == true) {
            return new ResponseEntity<>("Category name already exists", HttpStatus.CONFLICT);
        }
        productCategoryRepository.save(productCategory);
        return ResponseEntity.ok("success: true");
    }

    public ResponseEntity<List<ProductCategory>> getAllCategories() {
        try {
            List<ProductCategory> categories = productCategoryRepository.findAll();
            return ResponseEntity.ok(categories);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    public ResponseEntity<String> deleteProductCategory(String id) {
        try {
            if (productCategoryRepository.findById(id).isEmpty()) {
                return new ResponseEntity<>("Category not found", HttpStatus.NOT_FOUND);
            }
            productCategoryRepository.deleteById(id);
            return new ResponseEntity<>("{\"success\": true}", HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("An unexpected error occurred", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public boolean doesCategoryExist(String categoryName) {
        return productCategoryRepository.existsByName(categoryName);
    }

    public ResponseEntity<String> editProductCategory(String id, ProductCategory productCategory) {
        try {
            Optional<ProductCategory> oldProductCategory = productCategoryRepository.findById(id);
            if (oldProductCategory.isEmpty()) {
                return new ResponseEntity<>("Category does not exist", HttpStatus.NOT_FOUND);
            }
            if (productCategory.getName() == null)
                productCategory.setName(oldProductCategory.get().getName());
            if (productCategory.getDesc() == null)
                productCategory.setDesc(oldProductCategory.get().getDesc());
            productCategoryRepository.save(productCategory);
            return ResponseEntity.ok("success: true");
        } catch (Exception e) {
            return new ResponseEntity<>("An unexpected error occurred", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
