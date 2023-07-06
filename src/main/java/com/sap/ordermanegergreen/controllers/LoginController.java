package com.sap.ordermanegergreen.controllers;

import com.sap.ordermanegergreen.models.User;
import com.sap.ordermanegergreen.services.UserService;
import com.sap.ordermanegergreen.utils.JwtToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/login")
public class LoginController {
    private UserService userService;
    private JwtToken jwtToken;
    @Autowired
    public void UserController(UserService userService, JwtToken jwtToken) {
        this.userService = userService;
        this.jwtToken = jwtToken;
    }
    @PostMapping("/{email}/{password}")
    public ResponseEntity<String> logIn(@PathVariable("email") String email,
                                        @PathVariable("password") String password) {
        try {
            User user = userService.getUserByEmailAndPassword(email,password);
            String token = jwtToken.generateToken(user);
            return ResponseEntity.ok(token);
        } catch(ResponseStatusException ex ){
            return new ResponseEntity<>(ex.getMessage(),ex.getStatusCode());
        } catch(Exception ex ){
            return new ResponseEntity<>("Unexpected error, " + ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
