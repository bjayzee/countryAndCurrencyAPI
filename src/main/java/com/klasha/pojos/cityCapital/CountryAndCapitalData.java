package com.klasha.pojos.cityCapital;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
public class CountryAndCapitalData {
   private String capital;
   private String iso2;
   private String iso3;
   private String name;
}
