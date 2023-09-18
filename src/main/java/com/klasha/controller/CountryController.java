package com.klasha.controller;

import com.klasha.pojos.countryAndCurrency.CurrencyRequestBody;
import com.klasha.service.CountryAPIService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/countries")
@RequiredArgsConstructor
public class CountryController {

    final CountryAPIService countryAPIService;

    @GetMapping("/cities/{N}")
    public ResponseEntity<?> getMostPopulatedFromSelectedCities(@PathVariable Integer N){

        try {
            return ResponseEntity.ok(countryAPIService.getCitiesInCountryWithHighestPopulation(N));
        }catch (Exception e){
            return ResponseEntity.badRequest().body(e.getLocalizedMessage());
        }
    }
    @GetMapping("/{countryName}")
    public ResponseEntity<?> getCountryDetail(@PathVariable String countryName){

        try {
            return ResponseEntity.ok(countryAPIService.getCountryDetails(countryName));
        }catch (Exception e){
            return ResponseEntity.badRequest().body(e.getLocalizedMessage());
        }
    }
    @GetMapping("/states/{countryName}")
    public ResponseEntity<?> getCountryAndStateDetail(@PathVariable String countryName){

        try {
            return ResponseEntity.ok(countryAPIService.getCountryStatesAndCities(countryName));
        }catch (Exception e){
            return ResponseEntity.badRequest().body(e.getLocalizedMessage());
        }
    }
    @GetMapping("/currency")
    public ResponseEntity<?> getCurrencyConversion(@RequestBody CurrencyRequestBody requestBody){

        try {
            return ResponseEntity.ok(countryAPIService.getCountryCurrencyAndRate(requestBody));
        }catch (Exception e){
            return ResponseEntity.badRequest().body(e.getLocalizedMessage());
        }
    }

}
