package com.sap.ordermanagergreen.service;
import com.sap.ordermanagergreen.model.Currency;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
  public class CurrencyExchangeService {
    @Autowired
    private RedisTemplate<String, String> redisTemplate;
    @Autowired
    private RedisService redisService;

    public String getGate(Currency company, Currency order){
     String gate;
     //להפוך לtry ןctach
     if(redisService.findIfExist(company, order)!="not found"){
       return redisService.findIfExist(company, order);
     }
     else{
         gate="https://db29ee71-7252-4f02-a44c-59aa27e94540.mock.pstmn.io/EURO_DOLLAR";
        // gate="https://db29ee71-7252-4f02-a44c-59aa27e94540.mock.pstmn.io/${company}_{order}";
        System.out.println(gate);
     }
     //לעשות mock server
    return "4.6";
    }

}
