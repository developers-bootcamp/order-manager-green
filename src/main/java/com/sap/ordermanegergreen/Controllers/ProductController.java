//
//package com.sap.ordermanegergreen.Controllers;
//
//import com.sap.ordermanegergreen.DTO.ProductDto;
//import com.sap.ordermanegergreen.Models.Product;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//import org.springframework.web.server.ResponseStatusException;
//import com.sap.ordermanegergreen.Services.ProductService;
//
//import java.util.List;
//@RestController
//@RequestMapping ("/product")
//public class ProductController {
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
//    @GetMapping("/getAll")
//    public List<ProductDto>getAll(){
//
//    }
//    @PutMapping("/edit/{id)")
//    public ResponseEntity edit(@PathVariable String id,Product product){
//
//    }
//    @DeleteMapping("/deleteById/{id}")
//    public ResponseEntity deleteById(@PathVariable String id){
//
//    }
//}
//
//
