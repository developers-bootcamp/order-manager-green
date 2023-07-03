package com.sap.ordermanegergreen.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/order")
public class OrdersController {
    @GetMapping
    @RequestMapping("/getOrders")
    public String getOrders(){
        
    return "YAY!!!";
    }
}
