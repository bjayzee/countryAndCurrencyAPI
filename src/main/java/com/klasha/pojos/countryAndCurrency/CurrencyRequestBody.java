package com.klasha.pojos.countryAndCurrency;

import lombok.Data;

@Data
public class CurrencyRequestBody {
    private String countryName;
    private double amount;
    private String targetCurrency;
}
