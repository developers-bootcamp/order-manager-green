package com.sap.ordermanegergreen.Services;

import com.mongodb.client.FindIterable;
import com.sap.ordermanegergreen.DTO.ProductCategoryDTO;
import com.sap.ordermanegergreen.Models.ProductCategory;
import com.sap.ordermanegergreen.Models.User;
import com.sap.ordermanegergreen.Repositories.IProductCategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import com.sap.ordermanegergreen.DTO.ProductCategoryMapper;

import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class ProductCategoryService {
    IProductCategoryRepository ProductCategoryRepository;
    private final ProductCategoryMapper productCategoryMapper;
    @Autowired
    public ProductCategoryService(IProductCategoryRepository ProductCategoryRepository,ProductCategoryMapper productCategoryMapper) {
        this.ProductCategoryRepository = ProductCategoryRepository;

        this.productCategoryMapper = productCategoryMapper;
    }

    public ResponseEntity<String> saveProductCategory(ProductCategory productCategory) {
        String categoryName = productCategory.getName();
        if(doesCategoryExist(categoryName)==true){
            return new ResponseEntity<>("Category name already exists", HttpStatus.CONFLICT);
        }

        ProductCategoryRepository.save(productCategory);
        return ResponseEntity.ok("success: true");

    }

    public ResponseEntity<List<ProductCategoryDTO>> getAllCategories() {
        try {
            List<ProductCategory> productCategories = ProductCategoryRepository.findAll();
            List<ProductCategoryDTO> productCategoryDTOs = productCategoryMapper.toDtoList(productCategories);
            return ResponseEntity.ok(productCategoryDTOs);


        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

    }

    public ResponseEntity<String> deleteProductCategory(String id) {
        try {
            if(ProductCategoryRepository.findById(id).isEmpty()){
                return new ResponseEntity<>("Category not found", HttpStatus.NOT_FOUND);
            }
            ProductCategoryRepository.deleteById(id);
            return new ResponseEntity<>("{\"success\": true}", HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("An unexpected error occurred", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    public boolean doesCategoryExist(String categoryName) {
        // Check if the category name exists in the database
        return ProductCategoryRepository.existsByName(categoryName);
    }

    public ResponseEntity<String> editProductCategory(String id, ProductCategory productCategory) {
        try{
            Optional<ProductCategory> oldProductCategory=ProductCategoryRepository.findById(id);
            if(oldProductCategory.isEmpty()){
                return new ResponseEntity<>("Category does not exist",HttpStatus.NOT_FOUND);
            }
            if(productCategory.getName()==null)
                productCategory.setName(oldProductCategory.get().getName());
            if(productCategory.getDesc()==null)
                productCategory.setDesc(oldProductCategory.get().getDesc());
            ProductCategoryRepository.save(productCategory);
            return ResponseEntity.ok("success: true");
        }catch(Exception e){
            return new ResponseEntity<>("An unexpected error occurred", HttpStatus.INTERNAL_SERVER_ERROR);

        }
    }
}
