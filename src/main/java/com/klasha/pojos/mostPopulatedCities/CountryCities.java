package com.klasha.pojos.mostPopulatedCities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class CountryCities {
    private List<CityData> data;

}
