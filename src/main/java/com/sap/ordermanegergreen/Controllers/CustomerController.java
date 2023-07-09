package com.sap.ordermanegergreen.Controllers;

import com.sap.ordermanegergreen.Models.User;
import com.sap.ordermanegergreen.Services.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("http://localhost:3000")
@RequestMapping("/customer")
public class CustomerController {
    private CustomerService customerService;
    @Autowired
    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping
    @RequestMapping("/getAll")
    public List<User> getAll() {
        return customerService.getAll();
    }

    @GetMapping
    @RequestMapping("/getById/{id}")
    public User getById(@PathVariable String id) {
        return customerService.getById(id);
    }

    @PostMapping
    @RequestMapping("/add")
    public void add(@RequestBody User p) {
        customerService.add(p);
    }

    @PutMapping
    @RequestMapping("/editById/{id}")
    public User editById(@RequestBody User user, @PathVariable String id) {
        return customerService.put(user, id);
    }

    @DeleteMapping
    @RequestMapping("/deleteById/{id}")
    public void deleteById(@PathVariable String id) {
        customerService.deletebyId(id);
    }
}
