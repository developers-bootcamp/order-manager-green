
package com.sap.ordermanegergreen.controllers;

import com.sap.ordermanegergreen.dto.ProductDto;
import com.sap.ordermanegergreen.dto.TokenDTO;
import com.sap.ordermanegergreen.exception.NoPremissionException;
import com.sap.ordermanegergreen.exception.ObjectExistException;
import com.sap.ordermanegergreen.models.Product;
import com.sap.ordermanegergreen.utils.JwtToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.sap.ordermanegergreen.services.ProductService;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.List;

@RestController
@RequestMapping("/product")
public class ProductController {
    @Autowired
    private ProductService productService;
    @Autowired
    private JwtToken jwtToken;

    @GetMapping
    @RequestMapping("/createToken")
    public String createToken() {
        return productService.fill();
    }
    @GetMapping
    @RequestMapping("/getToken")
    public TokenDTO getTokenFromHeader(@RequestHeader("Authorization") String token) {
        return jwtToken.decodeToken(token);
    }

    @PostMapping
    public ResponseEntity add(@RequestHeader("Authorization") String token, @RequestBody Product product) {
        try {
            productService.add(product, token);
        } catch (ObjectExistException ex) {
            return new ResponseEntity(ex, HttpStatus.CONFLICT);
        } catch (NoPremissionException ex) {
            return new ResponseEntity(ex, HttpStatus.FORBIDDEN);
        } catch (Exception ex) {
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity(HttpStatus.OK);

    }
    @GetMapping("/getAllNames")
    public ResponseEntity getAllNames(@RequestHeader("Authorization") String token) {
        try {
            List<ProductDto> productsDto = productService.getAllNames(token);
            return ResponseEntity.ok(productsDto);
        } catch (Exception ex) {
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping("/getAll")
    public ResponseEntity getAll(@RequestHeader("Authorization") String token) {
        try {
            return ResponseEntity.ok(productService.getAll(token));
        } catch (Exception ex) {
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PutMapping("/{id}")
    public ResponseEntity editById(@RequestHeader("Authorization") String token, @PathVariable String id, @RequestBody Product product) {
        try {
            productService.editById(id, product, token);
        } catch (ObjectExistException ex) {
            return new ResponseEntity(ex, HttpStatus.CONFLICT);
        } catch (NoPremissionException ex) {
            return new ResponseEntity(ex, HttpStatus.FORBIDDEN);
        } catch (EmptyResultDataAccessException ex) {
            return new ResponseEntity("The product is not exist in the system", HttpStatus.NOT_FOUND);
        } catch (Exception ex) {
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity(HttpStatus.OK);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity deleteById(@RequestHeader("Authorization") String token, @PathVariable String id) {
        try {
            productService.deleteById(id, token);
            return new ResponseEntity(HttpStatus.OK);
        } catch (NoPremissionException ex) {
            return new ResponseEntity(ex, HttpStatus.FORBIDDEN);
        } catch (EmptyResultDataAccessException ex) {
            return new ResponseEntity("The product is not exist in the system", HttpStatus.NOT_FOUND);
        } catch (Exception ex) {
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}


