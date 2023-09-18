package com.klasha.pojos.countryPopulationData;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class CountryPopulation {

    private String error;

    private PopulationCount data;
}
