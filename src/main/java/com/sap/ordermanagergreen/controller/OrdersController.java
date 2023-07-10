package com.sap.ordermanagergreen.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin("http://localhost:3000")
@RestController
@RequestMapping("/orders")
public class OrdersController {
    
    @GetMapping
    public String getOrders(){    
    return "YAY!!!";
    }
    
}
