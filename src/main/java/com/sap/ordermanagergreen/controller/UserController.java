package com.sap.ordermanagergreen.controller;

import com.sap.ordermanagergreen.exception.NotValidException;
import com.sap.ordermanagergreen.exception.ObjectExistException;
import com.sap.ordermanagergreen.model.User;
import com.sap.ordermanagergreen.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("http://localhost:3000")
@RestController
@RequestMapping("/user")
public class UserController {

    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
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

}