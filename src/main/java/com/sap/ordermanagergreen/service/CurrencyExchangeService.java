package com.sap.ordermanagergreen.service;
import com.sap.ordermanagergreen.dto.RedisDto;
import com.sap.ordermanagergreen.model.Currency;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
@Service
  public class CurrencyExchangeService {
    @Autowired
    private RedisTemplate<String, String> redisTemplate;
    @Value("${mock.server.url}")
    private String mockServerUrl;
    private RestTemplate restTemplate;
    @Autowired
    private RedisService redisService;

    public CurrencyExchangeService() {
        restTemplate= new RestTemplate();
    }

    public String getGate(Currency company, Currency order) throws Exception {
     String gate;
     if (company.equals(order))
            return "1";
     try {
         gate=redisService.findIfExist(company, order);
         if(gate=="not found"){
             gate=getDataFromMockServer(company,order);
         }
         return gate;
     }
     catch (Exception e){
        throw new Exception("Error connecting to Redis: " + e.getMessage());
     }
    }
        public String getDataFromMockServer(Currency company, Currency order) {
            String exchangeRateApi = mockServerUrl+"/"+company+"_"+order;
            ResponseEntity<String> responseEntity = restTemplate.getForEntity(exchangeRateApi, String.class);
            if (responseEntity.getStatusCode().is2xxSuccessful()) {
                String rate = responseEntity.getBody();
                RedisDto newObject=RedisDto.builder().companyCurrency(company).orderCurrency(order).day(LocalDate.now()).build();
                redisService.addExchange(newObject, rate);
                return rate;
            } else {
                return "Error occurred: " + responseEntity.getStatusCode();
            }
        }
    }
