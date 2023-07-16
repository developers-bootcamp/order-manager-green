package com.sap.ordermanagergreen.controller;

import com.sap.ordermanagergreen.dto.TokenDTO;
import com.sap.ordermanagergreen.dto.UserDto;
import com.sap.ordermanagergreen.exception.NoPremissionException;
import com.sap.ordermanagergreen.exception.NotValidException;
import com.sap.ordermanagergreen.exception.ObjectExistException;
import com.sap.ordermanagergreen.model.User;
import com.sap.ordermanagergreen.service.UserService;
import com.sap.ordermanagergreen.util.JwtToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin("http://localhost:3000")
@RestController
@RequestMapping("/user")
public class UserController {
    private final UserService userService;
    private final JwtToken jwtToken;

    @Autowired
    public UserController(UserService userService, JwtToken jwtToken) {
        this.userService = userService;
        this.jwtToken = jwtToken;
    }

    @GetMapping
    @RequestMapping("/getAllUsers")
    public ResponseEntity<List<UserDto>> getAll(@RequestParam(defaultValue = "1") Integer page, @RequestParam(defaultValue = "5") Integer pageSize, @RequestHeader("Authorization") String token) {
        TokenDTO tokenDTO=JwtToken.decodeToken(token);
        List<UserDto> l = null;
        try {
            l = userService.getAll(tokenDTO.getCompanyId(),page,pageSize);
        }
        catch (Exception e) {
            System.out.println("💕💕 error "+e);
        }
        return ResponseEntity.ok(l);
    }
    @GetMapping
    @RequestMapping("/getAllByPrefix/{prefixName}")
    public ResponseEntity<Map<String,String>> getAllByPrefix(@PathVariable("prefixName") String prefixName, @RequestHeader("Authorization") String token) {
        TokenDTO tokenDTO = JwtToken.decodeToken(token);
        Map<String,String> l = null;
        Map<String, String> errorMap = new HashMap<>();
        try {
            l = userService.getAllByPrefix(prefixName,tokenDTO.getCompanyId());
        } catch (IllegalArgumentException e) {
            errorMap.put("IllegalArgumentException","invalid prefixName");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMap);
        } catch (Exception e) {
            errorMap.put("Exception",e.getMessage());
            return ResponseEntity.internalServerError().body(errorMap);
        }
        return ResponseEntity.ok(l);
    }
    @PostMapping
    @RequestMapping("/signUp")
    public ResponseEntity<String> signUp(@RequestParam("fullName") String fullName, @RequestParam("companyName") String companyName, @RequestParam("email") String email, @RequestParam("password") String password) {
        try {
            User user = userService.signUp(fullName, companyName, email, password);
            return ResponseEntity.ok(user.getFullName());
        } catch (ObjectExistException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
        } catch (NotValidException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>("Unexpected error Please try again later", HttpStatusCode.valueOf(500));
        }
    }

    @PostMapping
    public ResponseEntity<String> add(@RequestHeader("Authorization") String token, @RequestBody User user) {
        try {
            userService.add(token, user);
        } catch (ObjectExistException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.CONFLICT);
        } catch (Exception ex) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping
    @RequestMapping("/{userId}")
    public ResponseEntity<String> deleteById(@RequestHeader("Authorization") String token, @PathVariable String userId) {
        try {
            userService.deleteById(token, userId);
        } catch (ResponseStatusException ex) {
            return new ResponseEntity<>("User does not exist", HttpStatus.NOT_FOUND);
        } catch (NoPremissionException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.FORBIDDEN);
        } catch (Exception ex) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<String> editById(@RequestHeader("Authorization") String token, @RequestBody User user) {
        try {
            userService.editById(token, user);
        } catch (ResponseStatusException ex) {
            return new ResponseEntity<>("User does not exist", HttpStatus.NOT_FOUND);
        } catch (NoPremissionException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.FORBIDDEN);
        } catch (Exception ex) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping
    @RequestMapping("/{email}/{password}")
    public ResponseEntity<String> logIn(@PathVariable("email") String email, @PathVariable("password") String password) {
        try {
            User user = userService.getUserByEmailAndPassword(email, password);
            System.out.println(user);
            String token = jwtToken.generateToken(user);
            return ResponseEntity.ok(token);
        } catch (ResponseStatusException ex) {
            return new ResponseEntity<>(ex.getMessage(), ex.getStatusCode());
        } catch (Exception ex) {
            return new ResponseEntity<>("Unexpected error, " + ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}