package com.klasha.pojos.countryPopulationData;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public class PopulationFigure {
        private String year;
        private String value;
    }
