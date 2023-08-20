package com.sap.ordermanagergreen;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class OrderManagerGreenApplication {
    public static final String MY_URL = "http://localhost:3000";

    public static void main(String[] args) {
        SpringApplication.run(OrderManagerGreenApplication.class, args);
    }
}
