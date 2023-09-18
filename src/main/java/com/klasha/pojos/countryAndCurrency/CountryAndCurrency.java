package com.klasha.pojos.countryAndCurrency;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class CountryAndCurrency {
    private boolean error;
    private CountryCurrency data;
}
