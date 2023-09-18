package com.klasha.pojos.cityCapital;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class CountryAndCapitalPojo {
    private boolean error;
    private CountryAndCapitalData data;
}
