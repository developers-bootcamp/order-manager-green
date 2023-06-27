package com.sap.ordermanegergreen.Controllers;

import com.sap.ordermanegergreen.Models.User;
import com.sap.ordermanegergreen.Services.ICustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RestController("/Customer")
public class CustomerController {
    @Autowired
    private ICustomerService customerService;

    public CustomerController(ICustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping
    public List<User> getAll() {
        return customerService.getAll();
    }

    @GetMapping
    @RequestMapping("/{id}")
    public User getById(@PathVariable String id) {
        return customerService.getById(id);
    }

    @PostMapping
    public void add(@RequestBody User p) {
        customerService.add(p);
    }

    @PutMapping("{id}")
    public User put(@RequestBody User user, @PathVariable String id) {
        return customerService.put(user, id);
    }

    @DeleteMapping("{id}")
    public void deleteById(@PathVariable String id) {
        customerService.deletebyId(id);
    }
}
