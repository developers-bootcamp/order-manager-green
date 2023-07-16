package com.sap.ordermanegergreen.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@CrossOrigin("http://localhost:3000")
@RestController
@RequestMapping("order")
public class OrdersController {
    @GetMapping
    @RequestMapping("try")
    public String getOrders(){
    return "YAY!!!";
    }
}
