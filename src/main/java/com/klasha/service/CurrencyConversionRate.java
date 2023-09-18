package com.klasha.service;

import lombok.Data;

@Data
public class CurrencyConversionRate {
    private String sourceCurrency;
    private String targetCurrency;
    private double rate;

    public CurrencyConversionRate(String sourceCurrency, String targetCurrency, String rate) {
        this.sourceCurrency = sourceCurrency;
        this.targetCurrency = targetCurrency;
        this.rate = Double.parseDouble(rate);
    }
}
