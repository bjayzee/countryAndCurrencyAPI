package com.klasha.pojos.responseObject;

import lombok.Data;

@Data
public class CountryData {

    private String countryName;
    private String population;
    private String capital;
    private LocationDetail location;
    private String currency;
    private String iso2;
    private String iso3;
}
