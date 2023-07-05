package com.sap.ordermanegergreen.controller;

import com.sap.ordermanegergreen.dto.TokenDTO;
import com.sap.ordermanegergreen.model.AvailableRoles;
import com.sap.ordermanegergreen.model.Company;
import com.sap.ordermanegergreen.model.User;
import com.sap.ordermanegergreen.service.CustomerService;
import com.sap.ordermanegergreen.util.JwtToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/customer")
public class CustomerController {

    private CustomerService customerService;
    private JwtToken jwtToken;

    @Autowired
    public CustomerController(CustomerService customerService, JwtToken jwtToken) {
        this.jwtToken = jwtToken;
        this.customerService = customerService;
    }

    @GetMapping
    @RequestMapping("/getToken/{token}")
    public TokenDTO getToken(@PathVariable String token) {
        return jwtToken.decodeToken(token);
    }

    @GetMapping
    public List<User> getAll() {
        return customerService.getAll().stream().filter(c->c.getRoleId().equals(AvailableRoles.CUSTOMER)).toList();
    }

    @GetMapping("/{id}")
    public User getById(@PathVariable String id) {
        return customerService.getById(id);
    }

    @PostMapping
    public void add(@RequestBody User user) {
        customerService.add(user);
    }

    @PutMapping("/{id}")
    public User editById(@RequestBody User user, @PathVariable String id) {
        return customerService.put(id, user);
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable String id) {
        customerService.deletebyId(id);
    }

}
