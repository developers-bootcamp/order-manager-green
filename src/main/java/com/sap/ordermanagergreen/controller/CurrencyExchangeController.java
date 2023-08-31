package com.sap.ordermanagergreen.controller;

import com.sap.ordermanagergreen.dto.RedisDto;
import com.sap.ordermanagergreen.model.Currency;
import com.sap.ordermanagergreen.service.CurrencyExchangeService;
import com.sap.ordermanagergreen.service.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@CrossOrigin("http://localhost:3000")
@RestController
@RequestMapping("/currencyExchange")
public class CurrencyExchangeController {
      @Autowired
      private CurrencyExchangeService currencyExchangeService;
    @Autowired
      private RedisService redisService;

        @PostMapping("/add")
        public  void addToRedis(@RequestBody RedisDto ExchangeObject,@RequestParam("gate") String gate) {
            ExchangeObject.setDay(LocalDate.now());
            redisService.addExchange(ExchangeObject,gate);
        }

        @GetMapping
        public String getGate(@RequestParam("user") Currency user, @RequestParam("product") Currency product) {
            return redisService.findIfExist(user,product);
        }
    }
