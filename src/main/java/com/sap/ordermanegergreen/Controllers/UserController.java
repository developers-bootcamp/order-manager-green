package com.sap.ordermanegergreen.Controllers;

import com.sap.ordermanegergreen.Models.User;
import com.sap.ordermanegergreen.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
    @RequestMapping("/getAll")
    public List<User> getAll() {
        return userService.getAll();
    }

    @GetMapping
    @RequestMapping("/{userId}")
    public User getById(@PathVariable String userId) {
        return userService.getById(userId);
    }

    @PostMapping
    public void add(@RequestBody User user) {
        userService.add(user);
    }

    @PutMapping
    @RequestMapping(("/{userId}"))
    public User editById(@RequestBody User user, @PathVariable String userId) {
        return userService.editById(user, userId);
    }

    @DeleteMapping
    @RequestMapping(("/{userId}"))
    public void deleteById(@PathVariable String userId) {
        userService.deletebyId(userId);
    }
}
