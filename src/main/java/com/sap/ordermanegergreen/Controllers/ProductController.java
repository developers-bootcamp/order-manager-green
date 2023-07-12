
package com.sap.ordermanegergreen.Controllers;

import com.sap.ordermanegergreen.DTO.ProductDto;
import com.sap.ordermanegergreen.Models.DiscountTypes;
import com.sap.ordermanegergreen.Models.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;


import java.util.ArrayList;
import java.util.List;
@RestController
@CrossOrigin("http://localhost:3000")
@RequestMapping ("/product")
public class ProductController {
   // private ProductService ProductService;
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
    @GetMapping("/getAll")
    public List<Product>getAll(){
        ArrayList<Product> p=new ArrayList<>() ;
        Product prod=new Product();
        prod.setId("1");
        prod.setName("albom");
        prod.setPrice(10.5);
        prod.setDescription("a very nice albom");
        prod.setDiscount(2);
        prod.setDiscountType(DiscountTypes.FIXED_AMOUNT);
       p.add(prod);

        Product prod2=new Product();
        prod2.setId("2");
        prod2.setName("frame");
        prod2.setPrice(20.5);
        prod2.setDescription("a very nice metel frame");
        prod2.setDiscount(10);
        prod2.setDiscountType(DiscountTypes.PERCENTAGE);
        p.add(prod2);
        return p;

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


