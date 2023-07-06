package com.sap.ordermanegergreen.controllers;
import com.sap.ordermanegergreen.models.User;
import com.sap.ordermanegergreen.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import java.util.List;
@RestController
@RequestMapping("/user")
public class UserController {
    private UserService userService;
    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }
    @GetMapping
    public List<User> getAll() {
        return userService.getAll();
    }
    @GetMapping("/{id}")
    public User getById(@PathVariable String id) {
        return userService.getById(id);
    }
    @PostMapping
    public ResponseEntity add(@RequestHeader("Authorization")String token,@RequestBody User user){
        try{
            userService.add(token,user);
        }
        catch(ResponseStatusException ex) {
            return new ResponseEntity("user name already exist", HttpStatus.CONFLICT);
        }
        catch (Exception ex){
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity(HttpStatus.OK);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity deleteById(@RequestHeader("Authorization")String token,@PathVariable String id) {
        try {
            userService.deleteById(id,token);
        }
        catch(ResponseStatusException ex) {
            return new ResponseEntity("User does not exist", HttpStatus.NOT_FOUND);
        }
        catch (Exception ex){
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity(HttpStatus.OK);
    }
    @PutMapping("/{id}")
    public User editById(@RequestBody User user, @PathVariable String id) {
        return userService.editById(user, id);
    }
}