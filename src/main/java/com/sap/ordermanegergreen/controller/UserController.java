package com.sap.ordermanegergreen.controller;

import com.sap.ordermanegergreen.model.User;
import com.sap.ordermanegergreen.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
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
        return userService.signUp(fullName, companyName, email, password);
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
    public void add(@RequestBody User user) {
        userService.add(user);
    }

    @PutMapping("/{id}")
    public User editById(@PathVariable String id, @RequestBody User user) {
        return userService.put(id, user);
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable String id) {
        userService.deletebyId(id);
    }

}
