package com.sap.ordermanegergreen.Controllers;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/Orders")
public class OrdersController {
    @GetMapping
    @RequestMapping("/getOrders")
    public String getOrders(){
        
    return "YAY!!!";
    }
}
