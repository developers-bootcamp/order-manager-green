package com.sap.ordermanegergreen.Controllers;

import com.sap.ordermanegergreen.Models.User;
import com.sap.ordermanegergreen.Services.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RestController("/User")
public class UserController {
    @Autowired
    private IUserService userService;

    public UserController(IUserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public List<User> getAll() {
        return userService.getAll();
    }

    @GetMapping
    @RequestMapping("/{id}")
    public User getById(@PathVariable String id) {
        return userService.getById(id);
    }

    @PostMapping
    public void add(@RequestBody User p) {
        userService.add(p);
    }

    @PutMapping("{id}")
    public User put(@RequestBody User pupil, @PathVariable String id) {
        return userService.put(pupil, id);
    }

    @DeleteMapping("{id}")
    public void deleteById(@PathVariable String id) {
        userService.deletebyId(id);
    }
}
