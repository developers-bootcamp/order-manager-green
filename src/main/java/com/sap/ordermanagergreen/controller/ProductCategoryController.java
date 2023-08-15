package com.sap.ordermanagergreen.controller;

import com.sap.ordermanagergreen.dto.ProductCategoryDTO;
import com.sap.ordermanagergreen.exception.ObjectExistException;
import com.sap.ordermanagergreen.exception.ObjectNotExistException;
import com.sap.ordermanagergreen.exception.UnauthorizedException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import com.sap.ordermanagergreen.model.ProductCategory;
import org.springframework.beans.factory.annotation.Autowired;
import com.sap.ordermanagergreen.service.ProductCategoryService;

import java.util.List;

import static com.sap.ordermanagergreen.OrderManagerGreenApplication.MY_URL;

@CrossOrigin(MY_URL)
@RestController
@RequestMapping("/productCategory")
@Validated
public class ProductCategoryController {

    @Autowired
    private ProductCategoryService ProductCategoryService;

    @GetMapping
    public ResponseEntity<List<ProductCategoryDTO>> get(@RequestHeader("Authorization") String token) {
        try {
            return ResponseEntity.ok(ProductCategoryService.get(token));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping
    public ResponseEntity<String> add(@RequestHeader("Authorization") String token, @RequestBody ProductCategory productCategory) throws ObjectNotExistException {
        try {
            ProductCategoryService.add(token, productCategory);
        } catch (ObjectExistException objectExistException) {
            return new ResponseEntity<>(objectExistException.getMessage(), HttpStatus.CONFLICT);
        } catch (UnauthorizedException unauthorizedException) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        } catch (Exception e) {
            return new ResponseEntity<>("An unexpected error occurred", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return ResponseEntity.ok("success: true");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@RequestHeader("Authorization") String token, @PathVariable("id") String id) {
        try {
            ProductCategoryService.delete(token, id);
        } catch (ObjectNotExistException objectNotExistException) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(objectNotExistException.getMessage());
        } catch (Exception e) {
            return new ResponseEntity<>("An unexpected error occurred", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>("{\"success\": true}", HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> update(@PathVariable("id") String id, @RequestBody ProductCategory productCategory, @RequestHeader("Authorization") String token) {
        try {
            ProductCategoryService.update(id, productCategory, token);
        } catch (ObjectNotExistException objectNotExistException) {
            return new ResponseEntity<>(objectNotExistException.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>("An unexpected error occurred", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return ResponseEntity.ok("success: true");
    }

}