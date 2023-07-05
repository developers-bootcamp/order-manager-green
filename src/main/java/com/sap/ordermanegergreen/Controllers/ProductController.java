
package com.sap.ordermanegergreen.Controllers;

import com.sap.ordermanegergreen.DTO.ProductDto;
import com.sap.ordermanegergreen.DTO.TokenDTO;
import com.sap.ordermanegergreen.Exception.NoPremissionException;
import com.sap.ordermanegergreen.Exception.ObjectExistException;
import com.sap.ordermanegergreen.Models.Product;
import com.sap.ordermanegergreen.Utils.JwtToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import com.sap.ordermanegergreen.Services.ProductService;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.List;
@RestController
@RequestMapping ("/product")
public class ProductController {
    @Autowired
    private ProductService ProductService;
    @Autowired
    private JwtToken jwtToken;


    @GetMapping
    @RequestMapping("/getToken")
    public TokenDTO getTokenFromHeader(@RequestHeader("Authorization") String token) {
        // token will contain the value of the Authorization header
        return  jwtToken.decodeToken(token);
    }

    @PostMapping
    public ResponseEntity add(@RequestHeader("Authorization") String token,@RequestBody Product product) {
        try {
            ProductService.addProduct(product, token);

        } catch (ObjectExistException ex) {
            return new ResponseEntity(ex, HttpStatus.CONFLICT);

    }
        catch (NoPremissionException ex){
            return new ResponseEntity(ex, HttpStatus.FORBIDDEN);

        }
        catch (Exception ex) {
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);

        }

        return new ResponseEntity(HttpStatus.OK);

    }
    @GetMapping("/getAllNames")
    public ResponseEntity getAllNames(@RequestHeader("Authorization") String token){
try{
    List<ProductDto>productsDto= ProductService.getAllNames(token);
    return  ResponseEntity.ok(productsDto);
}
catch (Exception ex){
    return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
}

    }
    @GetMapping("/getAll")
    public List<Product>getAll(@RequestHeader("Authorization") String token){
    return ProductService.getAll(token);
    }
    @PutMapping("/edit/{id)")
    public ResponseEntity edit(@RequestHeader("Authorization") String token,@PathVariable String id,Product product){
        try {
             ProductService.editById(id,product,token);

        }
        catch (Exception ex){
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity(HttpStatus.OK);

    }
    @DeleteMapping("/{id}")
    public ResponseEntity deleteById(@RequestHeader("Authorization") String token,@PathVariable String id) {
        try {
            ProductService.deleteById(id, token);
            return new ResponseEntity(HttpStatus.OK);
        } catch (NoPremissionException ex) {
            return new ResponseEntity(ex, HttpStatus.FORBIDDEN);
        } catch (EmptyResultDataAccessException ex) {
            return new ResponseEntity("The product is not exist in the system", HttpStatus.NOT_FOUND);
        }
        catch (Exception ex){
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);

        }
    }
    }


