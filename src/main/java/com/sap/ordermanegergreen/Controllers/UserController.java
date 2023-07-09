package com.sap.ordermanegergreen.Controllers;

import com.sap.ordermanegergreen.Exception.NoPremissionException;
import com.sap.ordermanegergreen.Exception.ObjectExistException;
import com.sap.ordermanegergreen.Models.User;
import com.sap.ordermanegergreen.Services.UserService;
import com.sap.ordermanegergreen.Utils.JwtToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/user")
public class UserController {
    private UserService userService;
    private JwtToken jwtToken;

    @Autowired
    public UserController(UserService userService, JwtToken jwtToken) {
        this.userService = userService;
        this.jwtToken = jwtToken;
    }

    @PostMapping
    public ResponseEntity<String>add(@RequestHeader("Authorization") String token, @RequestBody User user) {
        try {
            userService.add(token,user);
        } catch (ObjectExistException ex) {
            return new ResponseEntity(ex,HttpStatus.CONFLICT);
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
        } catch (NoPremissionException ex){
           return new ResponseEntity(ex, HttpStatus.FORBIDDEN);
        }catch (Exception ex) {
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
        }catch (NoPremissionException ex){
            return new ResponseEntity(ex, HttpStatus.FORBIDDEN);}
        catch (Exception ex) {
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
