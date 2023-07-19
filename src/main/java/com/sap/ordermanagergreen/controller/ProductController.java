
package com.sap.ordermanagergreen.controller;
import com.sap.ordermanagergreen.dto.TokenDTO;
import com.sap.ordermanagergreen.exception.NoPremissionException;
import com.sap.ordermanagergreen.exception.ObjectExistException;
import com.sap.ordermanagergreen.model.Product;
import com.sap.ordermanagergreen.util.JwtToken;
import com.sap.ordermanagergreen.model.Company;
import com.sap.ordermanagergreen.model.DiscountType;
import com.sap.ordermanagergreen.model.ProductCategory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.sap.ordermanagergreen.service.ProductService;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.List;

@RestController
@RequestMapping("/product")
public class ProductController {
    @Autowired
    private ProductService productService;
    @Autowired
    private JwtToken jwtToken;

    @GetMapping("/createToken")
    public String createToken() {
        return productService.fill();
    }
    @GetMapping("/getToken")
    public TokenDTO getTokenFromHeader(@RequestHeader("Authorization") String token) {
        return jwtToken.decodeToken(token);
    }

    @PostMapping
    public ResponseEntity add(@RequestHeader("Authorization") String token, @RequestBody Product product) {
        try{
            productService.add(product, token);
        } catch (ObjectExistException ex) {
            return new ResponseEntity(ex, HttpStatus.CONFLICT);
        } catch (NoPremissionException ex) {
            return new ResponseEntity(ex, HttpStatus.FORBIDDEN);
        } catch (Exception ex) {
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity(HttpStatus.OK);}

        @GetMapping("/getAllNames/{prefix}")
        public ResponseEntity getAllNames(@RequestHeader("Authorization") String token, @PathVariable String prefix) {
            try {
                return ResponseEntity.ok(productService.getAllNames(token,prefix));
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