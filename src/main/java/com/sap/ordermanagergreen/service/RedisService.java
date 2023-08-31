package com.sap.ordermanagergreen.service;

import com.sap.ordermanagergreen.dto.RedisDto;
import com.sap.ordermanagergreen.model.Currency;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class RedisService {
    @Autowired
    private  RedisTemplate<String, String> redisTemplate;
    //זמני
//    @Autowired
//    private

    public void addExchange(RedisDto key, String value) {
        redisTemplate.opsForValue().set(key.toKey(), value);
    }

    public String findIfExist(Currency company,Currency order) {
        RedisDto ExchangeObject=RedisDto.builder().companyCurrency(company).orderCurrency(order).day(LocalDate.now()).build();
        if(redisTemplate.hasKey(ExchangeObject.toKey())){
            return redisTemplate.opsForValue().get(ExchangeObject.toKey());
        }
        return "not found";
    }

    // Add more methods for other Redis operations
}
