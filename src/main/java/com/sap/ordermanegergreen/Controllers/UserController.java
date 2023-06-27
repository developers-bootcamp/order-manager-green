package com.sap.ordermanegergreen.Controllers;

import com.sap.ordermanegergreen.Models.User;
import com.sap.ordermanegergreen.Services.IUserService;
import com.sap.ordermanegergreen.Utils.JwtToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Controller
@RequestMapping("/User")
public class UserController{
   private IUserService UserService;
   private JwtToken jwtToken;
   @Autowired
   public UserController(IUserService UserService,JwtToken jwtToken){
       this.UserService=UserService;
       this.jwtToken= jwtToken;
 }

    @GetMapping("/check")
    //@ResponseBody
    public String index( String id) {
        return "Greetings from Spring Boot!";
    }

    @GetMapping("/get")
    //@ResponseBody
    public User GettingUser(String id) {
        return UserService.getUser(id);
    }
    @GetMapping("/{email}/{password}")
    public ResponseEntity<String> logIn(@PathVariable("email") String email,
                                        @PathVariable("password") String password) {
        try {
            User user = UserService.getUserByEmailAndPassword(email, password);
            String token = jwtToken.generateToken(user);
            return ResponseEntity.ok(token);
        } catch(ResponseStatusException ex ){
            return new ResponseEntity<>(ex.getMessage(),ex.getStatusCode());
        } catch(Exception ex ){
            return new ResponseEntity<>("Unexpected error, " + ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
