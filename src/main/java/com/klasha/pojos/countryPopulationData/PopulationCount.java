package com.klasha.pojos.countryPopulationData;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class PopulationCount {
    private String iso3;

    private List<PopulationFigure> populationCounts;

}
