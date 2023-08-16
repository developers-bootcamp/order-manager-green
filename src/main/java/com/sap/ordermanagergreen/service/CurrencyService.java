package com.sap.ordermanagergreen.service;

import com.sap.ordermanagergreen.model.Currency;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CurrencyService {

    public List<String> get() {
        List<String> names = new ArrayList<>();
        for (Currency currency : Currency.values()) {
            names.add(currency.name());
        }
        return names;
    }

}