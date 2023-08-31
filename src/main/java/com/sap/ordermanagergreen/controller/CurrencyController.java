package com.sap.ordermanagergreen.controller;

import com.sap.ordermanagergreen.service.CurrencyService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import static com.sap.ordermanagergreen.OrderManagerGreenApplication.MY_URL;

@CrossOrigin(MY_URL)
@RestController
@RequestMapping("/currency")
public class CurrencyController {

    @Autowired

    private CurrencyService currencyService;

    @GetMapping
    public List<String> get() {
        return currencyService.get();
    }

}