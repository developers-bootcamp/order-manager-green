
package com.sap.ordermanagergreen.controller;

import com.sap.ordermanagergreen.model.DiscountTypes;
import com.sap.ordermanagergreen.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin("http://localhost:3000")
@RestController
@RequestMapping ("/product")
public class ProductController {
//    private ProductService ProductService;
//    @Autowired
//    public ProductController(ProductService ProductService){
//        this.ProductService=ProductService;
//    }
//    @PostMapping("/add")
//    public ResponseEntity add(Product product){
//        try{
//            ProductService.addProduct(product);
//
//        }
//        catch(ResponseStatusException ex) {
//            return new ResponseEntity("product name already exist",HttpStatus.CONFLICT);
//
//        }
//        catch (Exception ex){
//            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
//
//        }
//        return new ResponseEntity(HttpStatus.OK);
//
//    }
//    @GetMapping("/getAllNames")
//    public List<ProductDto>getAllNames(){
//
//    }
    @GetMapping
    public List<Product>getAll(){
Product p= new Product("1","book","very intresting",10.2,10, DiscountTypes.FIXED_AMOUNT,"1",10,"yefe nof",null);
Product p2= new Product("2","book2","very boaring",10.2,10, DiscountTypes.PERCENTAGE,"1",10,"yefe nof",null);
Product p3= new Product("3","book3","very nice",18.2,10, DiscountTypes.PERCENTAGE,"1",10,"yefe nof",null);
List<Product> l=new ArrayList<Product>();
l.add(p);
l.add(p2);
l.add(p3);
return l;

    }
//    @PutMapping("/edit/{id)")
//    public ResponseEntity edit(@PathVariable String id,Product product){
//
//    }
//    @DeleteMapping("/deleteById/{id}")
//    public ResponseEntity deleteById(@PathVariable String id){
//
//    }
}


