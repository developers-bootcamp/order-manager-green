//package com.sap.ordermanegergreen.Controllers;
//
//import com.sap.ordermanegergreen.Models.Product_Category;
//import com.sap.ordermanegergreen.Repositorys.CategoryRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//@RestController
//@RequestMapping("/api/categories")
//public class ProductCategoryController {
//    private final CategoryRepository categoryRepository;
//
//    @Autowired
//    public ProductCategoryController(CategoryRepository categoryRepository) {
//        this.categoryRepository = categoryRepository;
//    }
//
//    @PostMapping
//    public ResponseEntity<?> addCategory(@RequestBody Product_Category category) {
//        String categoryName = category.getName();
//
//        // Check if the category name already exists
//        if (categoryRepository.existsByName(categoryName)) {
//            return ResponseEntity.status(HttpStatus.CONFLICT)
//                    .body("Category with name " + categoryName + " already exists.");
//        }
//
//        try {
//            Product_Category savedCategory = categoryRepository.save(category);
//            return ResponseEntity.ok("Category added successfully: " + savedCategory);
//        } catch (Exception e) {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
//                    .body("Unexpected error occurred while adding the category.");
//        }
//    }
//}
//
