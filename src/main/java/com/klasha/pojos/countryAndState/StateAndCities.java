package com.klasha.pojos.countryAndState;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class StateAndCities {
    private List<String> data;
}
