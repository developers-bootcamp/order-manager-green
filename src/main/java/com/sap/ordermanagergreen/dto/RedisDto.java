package com.sap.ordermanagergreen.dto;

import com.sap.ordermanagergreen.model.Currency;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import java.io.Serializable;
import java.time.LocalDate;
@Data
@AllArgsConstructor
@Builder
public class RedisDto implements Serializable {

    private Currency companyCurrency;
    private Currency orderCurrency;
    private LocalDate day;

    @Builder
    public RedisDto(Currency companyCurrency, Currency orderCurrency) {
        this.companyCurrency = companyCurrency;
        this.orderCurrency = orderCurrency;
    }
    public RedisDto(){
        day=LocalDate.now();
    }
    public String toKey(){
        return companyCurrency+" "+orderCurrency+" "+day;
    }
}
