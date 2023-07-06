//package com.sap.ordermanegergreen.controllers;
//
//import com.sap.ordermanegergreen.models.User;
//import com.sap.ordermanegergreen.services.CustomerService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//
//@RestController
//@RequestMapping("/customer")
//public class CustomerController {
//    private CustomerService customerService;
//    @Autowired
//    public CustomerController(CustomerService customerService) {
//        this.customerService = customerService;
//    }
//
//    @GetMapping
//    public List<User> getAll() {
//        return customerService.getAll();
//    }
//
//    @GetMapping
//    @RequestMapping("/{id}")
//    public User getById(@PathVariable String id) {
//        return customerService.getById(id);
//    }
//
//    @PostMapping
//    public void add(@RequestBody User p) {
//        customerService.add(p);
//    }
//
//    @PutMapping
//    @RequestMapping("/{id}")
//    public User editById(@RequestBody User user, @PathVariable String id) {
//        return customerService.put(user, id);
//    }
//
//    @DeleteMapping
//    @RequestMapping("/{id}")
//    public void deleteById(@PathVariable String id) {
//        customerService.deletebyId(id);
//    }
//}
