package com.klasha.pojos.countryLocation;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class LocationData {
    @JsonProperty(value = "long")
    private Long longitude;
    @JsonProperty(value = "lat")
    private Long latitude;
}
