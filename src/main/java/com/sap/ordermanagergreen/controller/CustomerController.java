package com.sap.ordermanagergreen.controller;

import com.sap.ordermanagergreen.dto.TokenDTO;
import com.sap.ordermanagergreen.model.AvailableRoles;
import com.sap.ordermanagergreen.model.User;
import com.sap.ordermanagergreen.service.CustomerService;
import com.sap.ordermanagergreen.util.JwtToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin("http://localhost:3000")
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
         //List<User>u= customerService.getAll().stream().filter(c->c.getRoleId().equals(AvailableRoles.CUSTOMER)).toList();
        List<User>u=new ArrayList<User>() ;
        User o=new User("1","me","21354",null,null,null,null);
        u.add(o);
         return u;
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
        customerService.deleteById(id);
    }

}
