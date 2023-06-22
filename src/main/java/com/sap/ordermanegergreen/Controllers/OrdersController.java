package com.sap.ordermanegergreen.Controllers;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")

public class OrdersController {
    @GetMapping
    public String getOrders(){
        
    return "YAY!!!";
    }
}
