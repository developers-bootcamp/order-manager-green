package com.sap.ordermanegergreen.Controllers;


import com.sap.ordermanegergreen.DTO.ProductDto;
import com.sap.ordermanegergreen.Models.Product;
import com.sap.ordermanegergreen.Services.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Controller
@RestController("/Product")
public class ProductController {
    IProductService ProductService;
    @Autowired
    public ProductController(IProductService ProductService){
        this.ProductService=ProductService;
    }
    @PostMapping("/add")
    public ResponseEntity addProduct(Product product){
        try{
            ProductService.addProduct(product);
        }

        catch(ResponseStatusException ex) {
            return new ResponseEntity();

        }
        catch (Exception ex){
            return new ResponseEntity(HttpStatus)

        }
        return new ResponseEntity(HttpStatus.OK);

    }
//    @GetMapping("/getAllProductsName")
//    public List<ProductDto>getAllProductsName(){
//
//    }
//    @GetMapping("/getProductById")
//    public List<ProductDto>getProductById(){
//
//    }
//    @PutMapping("/EditProduct")
//    public ResponseEntity EditProduct(int productId,Product product){
//
//    }
//    @DeleteMapping("/Deleteproduct")
//    public ResponseEntity DeleteProduct(int productId){
//
//    }
}


