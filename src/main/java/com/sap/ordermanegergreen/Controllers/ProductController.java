package com.sap.ordermanegergreen.Controllers;


import com.sap.ordermanegergreen.Services.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller
@RestController("/Product")
public class ProductController {
    IProductService ProductService;
    @Autowired
    public ProductController(IProductService ProductService){
        this.ProductService=ProductService;
    }
    @PostMapping("/add")
    public ResponseEntity addProduct(ProductDto product){
        try{
            ProductService.addProduct(product);
        }
        catch(Exception ) {
            return new ResponseEntity(HttpStatus.)

        }
        return new ResponseEntity(HttpStatus.OK);

    }
}


