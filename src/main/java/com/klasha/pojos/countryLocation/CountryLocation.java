package com.klasha.pojos.countryLocation;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class CountryLocation {
    private LocationData data;

    private String error;
}
