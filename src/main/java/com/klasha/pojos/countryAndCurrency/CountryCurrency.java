package com.klasha.pojos.countryAndCurrency;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class CountryCurrency {
    private String currency;
}
