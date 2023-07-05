package com.sap.ordermanegergreen.Controllers;
import com.sap.ordermanegergreen.DTO.ProductCategoryDTO;
import com.sap.ordermanegergreen.DTO.ProductCategoryMapper;
import com.sap.ordermanegergreen.Exeption.InternalServerException;
import com.sap.ordermanegergreen.Exeption.ObjectAlreadyExistsExeption;
import com.sap.ordermanegergreen.Exeption.ObjectNotFoundExeption;
import com.sap.ordermanegergreen.Exeption.UnauthorizedExeption;
import com.sap.ordermanegergreen.Models.ProductCategory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
    public ResponseEntity<List<ProductCategoryDTO>> getAll(@RequestHeader("Authorization") String token) {
        try {
            return ProductCategoryService.getAllCategories(token);
    }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping("/")
    public ResponseEntity<String> add(@RequestHeader("Authorization") String token, @RequestBody ProductCategory productCategory){
        try {
            return ProductCategoryService.saveProductCategory(token, productCategory);
        }catch (ObjectAlreadyExistsExeption objectAlreadyExistsExeption){
            return new ResponseEntity<>(objectAlreadyExistsExeption.getMessage(), HttpStatus.CONFLICT);
        }catch (ObjectNotFoundExeption objectNotFoundExeption){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(objectNotFoundExeption.getMessage());
        }catch (UnauthorizedExeption unauthorizedExeption) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }catch (Exception e) {
            return new ResponseEntity<>("An unexpected error occurred", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteById(@RequestHeader("Authorization") String token ,@PathVariable("id") String id) {
        try {
            return ProductCategoryService.deleteProductCategory(token, id);
        } catch (ObjectNotFoundExeption objectNotFoundExeption) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(objectNotFoundExeption.getMessage());
        } catch (Exception e) {
            return new ResponseEntity<>("An unexpected error occurred", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PutMapping("/{id}")
    public ResponseEntity<String> editProductCategory(@PathVariable("id") String id, @RequestBody ProductCategory productCategory,@RequestHeader("Authorization") String token){
      try {
        return ProductCategoryService.editProductCategory(id,productCategory,token);
      }catch (ObjectNotFoundExeption objectNotFoundExeption){
          return new ResponseEntity<>(objectNotFoundExeption.getMessage(),HttpStatus.NOT_FOUND);
      } catch(Exception e){
          return new ResponseEntity<>("An unexpected error occurred", HttpStatus.INTERNAL_SERVER_ERROR);
      }
    }
}

