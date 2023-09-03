package com.sap.ordermanagergreen.controller;

import com.sap.ordermanagergreen.dto.*;
import com.sap.ordermanagergreen.exception.NotValidException;
import com.sap.ordermanagergreen.exception.ObjectExistException;
import com.sap.ordermanagergreen.exception.NoPermissionException;
import com.sap.ordermanagergreen.model.AvailableRole;
import com.sap.ordermanagergreen.model.User;
import com.sap.ordermanagergreen.service.UserService;
import com.sap.ordermanagergreen.util.JwtToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.sap.ordermanagergreen.OrderManagerGreenApplication.MY_URL;

@CrossOrigin(MY_URL)
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<List<UserDTO>> get(@RequestParam(defaultValue = "1") Integer page, @RequestParam(defaultValue = "9") Integer pageSize, @RequestHeader("Authorization") String token) {
        TokenDTO tokenDTO = JwtToken.decodeToken(token);
        List<UserDTO> l = null;
        try {
            l = userService.get(tokenDTO.getCompanyId(), page, pageSize,AvailableRole.ADMIN);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
        return ResponseEntity.ok(l);
    }

    @GetMapping("/{prefixName}")
    public ResponseEntity<Map<String, String>> getNames(@PathVariable("prefixName") String prefixName, @RequestHeader("Authorization") String token) {
        TokenDTO tokenDTO = JwtToken.decodeToken(token);
        Map<String, String> l = null;
        Map<String, String> errorMap = new HashMap<>();
        try {
            l = userService.getNames(prefixName, tokenDTO.getCompanyId());
        } catch (IllegalArgumentException e) {
            errorMap.put("IllegalArgumentException", "invalid prefixName");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMap);
        } catch (Exception e) {
            errorMap.put("Exception", e.getMessage());
            return ResponseEntity.internalServerError().body(errorMap);
        }
        return ResponseEntity.ok(l);
    }

    @PostMapping("/signUp")
    public ResponseEntity<String> signUp(@RequestParam("fullName") String fullName, @RequestParam("companyName") String companyName, @RequestParam("email") String email, @RequestParam("password") String password, @RequestParam("currency") String currency) {
        try {
            User user = userService.signUp(fullName, companyName, email, password, currency);
            return logIn(email, password);
        } catch (NotValidException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (ObjectExistException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
        } catch (Exception e) {
            return new ResponseEntity<>("Unexpected error Please try again later", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{email}/{password}")
    public ResponseEntity<String> logIn(@PathVariable("email") String email, @PathVariable("password") String password) {
        try {
            User user = userService.logIn(email, password);
            System.out.println(user);
            String token = JwtToken.generateToken(user);
            return ResponseEntity.ok(token);
        } catch (ResponseStatusException ex) {
            return new ResponseEntity<>(ex.getMessage(), ex.getStatusCode());
        } catch (Exception ex) {
            return new ResponseEntity<>("Unexpected error, " + ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping
    public ResponseEntity<String> add(@RequestHeader("Authorization") String token, @Valid @RequestBody UserDTO user) {
        try {
            userService.add(token, user);
        } catch (ObjectExistException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.CONFLICT);
        } catch (NotValidException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
        catch (Exception ex) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> update(@PathVariable String id,@RequestHeader("Authorization") String token, @RequestBody UserDTO user) {
        try {
            userService.update(token, user,id);
        } catch (ResponseStatusException ex) {
            return new ResponseEntity<>("User does not exist", HttpStatus.NOT_FOUND);
        } catch (NoPermissionException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.FORBIDDEN);
        } catch (NotValidException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);

        } catch (Exception ex) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<String> delete(@RequestHeader("Authorization") String token, @PathVariable String userId) {
        try {
            userService.delete(token, userId);
        } catch (ResponseStatusException ex) {
            return new ResponseEntity<>("User does not exist", HttpStatus.NOT_FOUND);
        } catch (NoPermissionException ex) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(ex.getMessage());
        } catch (Exception ex) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }
}