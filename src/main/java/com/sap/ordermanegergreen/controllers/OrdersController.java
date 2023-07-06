package com.sap.ordermanegergreen.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/Orders")
public class OrdersController {
    @GetMapping
    public String getOrders(){
        
    return "YAY!!!";
    }
}
