package com.klasha.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.klasha.pojos.cityCapital.CountryAndCapitalPojo;
import com.klasha.pojos.countryAndCurrency.CountryAndCurrency;
import com.klasha.pojos.countryAndCurrency.CurrencyRequestBody;
import com.klasha.pojos.countryAndState.CountryAndState;
import com.klasha.pojos.countryAndState.StateAndCities;
import com.klasha.pojos.countryAndState.States;
import com.klasha.pojos.countryLocation.CountryLocation;
import com.klasha.pojos.countryPopulationData.CountryPopulation;
import com.klasha.pojos.mostPopulatedCities.CityData;
import com.klasha.pojos.mostPopulatedCities.CountryCities;
import com.klasha.pojos.responseObject.CountryData;
import com.klasha.pojos.responseObject.LocationDetail;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class CountryAPIService {

    final RestTemplate restTemplate;

    final ObjectMapper mapper;


    public List<String> getCitiesInCountryWithHighestPopulation(Integer N){
        // Define the API URL
        String apiUrl = "https://countriesnow.space/api/v0.1/countries/population/cities/filter";

        //for most populated cities in Italy
        Map<String, String> params = getCountryPopulatedCitiesParams("italy", N);

        CountryCities italyData = restTemplate.postForObject(apiUrl, params, CountryCities.class);
        assert italyData != null;
        List<String> mostPopulatedCities = new ArrayList<>(italyData.getData().stream().map(CityData::getCity).toList());



        //for most populated cities in New Zealand
        Map<String, String> paramsNZ = getCountryPopulatedCitiesParams("new zealand", N);

        CountryCities newZealandData = restTemplate.postForObject(apiUrl, paramsNZ, CountryCities.class);
        assert newZealandData != null;
        mostPopulatedCities.addAll(newZealandData.getData().stream().map(CityData::getCity).toList());



        //for most populated cities in Ghana
        Map<String, String> paramsGH = getCountryPopulatedCitiesParams("ghana", N);

        CountryCities ghanaData = restTemplate.postForObject(apiUrl, paramsGH, CountryCities.class);
        assert ghanaData != null;
        mostPopulatedCities.addAll(ghanaData.getData().stream().map(CityData::getCity).toList());


        return mostPopulatedCities;
      }
      private Map<String, String> getCountryPopulatedCitiesParams(String countryName, Integer numOfCity){
          Map<String, String> params = new HashMap<String, String>();
          params.put("limit", numOfCity.toString());
          params.put("order", "dsc");
          params.put("orderBy", "population");
          params.put("country", countryName);

          return params;
      }
    public CountryData getCountryDetails(String countryName) {

        Map<String, String> params = new HashMap<String, String>();
        params.put("country", countryName);

        CountryData countryData = new CountryData();

        //for Population
        CountryPopulation response = restTemplate.postForObject("https://countriesnow.space/api/v0.1/countries/population", params, CountryPopulation.class);
        assert response != null;
        int lastIndex = response.getData().getPopulationCounts().size();
        String latestPopulation = response.getData().getPopulationCounts().get(lastIndex -1).getValue();
        countryData.setPopulation(latestPopulation);

        //for capital city
        CountryAndCapitalPojo capitalCity = restTemplate.postForObject("https://countriesnow.space/api/v0.1/countries/capital", params, CountryAndCapitalPojo.class);
        assert capitalCity != null;
        countryData.setCapital(capitalCity.getData().getCapital());
        countryData.setCountryName(capitalCity.getData().getName());
        //for iso2
        countryData.setIso2(capitalCity.getData().getIso2());

        //for iso3
        countryData.setIso3(capitalCity.getData().getIso3());


        //for location
        CountryLocation location = restTemplate.postForObject("https://countriesnow.space/api/v0.1/countries/positions", params, CountryLocation.class);
        assert location != null;
        countryData.setLocation(new LocationDetail(location.getData().getLongitude(), location.getData().getLatitude()));

        //for currency
        CountryAndCurrency currency = restTemplate.postForObject("https://countriesnow.space/api/v0.1/countries/currency", params, CountryAndCurrency.class);
        assert currency != null;
        countryData.setCurrency(currency.getData().getCurrency());

        return countryData;
    }
    public Map<String, List<String>> getCountryStatesAndCities(String countryName){

        Map<String, String> params = new HashMap<String, String>();
        params.put("country", countryName);
//
        CountryAndState countryAndState = restTemplate.postForObject("https://countriesnow.space/api/v0.1/countries/states", params, CountryAndState.class);
        System.out.println(countryAndState);
        assert countryAndState != null;

        Map<String, List<String>> stateAndCities = new HashMap<>();
        for (States state : countryAndState.getData().getStates()) {
            Map<String, String> stateParams = new HashMap<String, String>();
            stateParams.put("country", countryName);
            stateParams.put("state", state.getName());
            StateAndCities stateCities = restTemplate.postForObject("https://countriesnow.space/api/v0.1/countries/states/cities", stateParams, StateAndCities.class);
            System.out.println(stateCities);
            assert stateCities != null;
            stateAndCities.put(state.getName(), stateCities.getData().stream().toList());
        }
        return stateAndCities;
    }
    public Map<String, Double> getCountryCurrencyAndRate(CurrencyRequestBody requestBody) throws IOException {
        Map<String, String> params = new HashMap<String, String>();
        params.put("country", requestBody.getCountryName());
        CountryAndCurrency currency = restTemplate.postForObject("https://countriesnow.space/api/v0.1/countries/currency", params, CountryAndCurrency.class);
        assert currency != null;

        double amountConverted = 1;
        String countryCurrency = currency.getData().getCurrency();
        for(CurrencyConversionRate rate: getCSVData()){
            if(rate.getSourceCurrency().equals(countryCurrency) && rate.getTargetCurrency().equals(requestBody.getTargetCurrency())){
                amountConverted = rate.getRate() * requestBody.getAmount();
            }
        }
        Map<String, Double> currencyResponse = new HashMap<>();
        currencyResponse.put(countryCurrency, amountConverted);
        return currencyResponse;
    }
    public List<CurrencyConversionRate> getCSVData() throws IOException {
        List<CurrencyConversionRate> conversionRates = new ArrayList<>();
        Path path = Path.of("src/exchange_rate (1).csv");
        Files.lines(path).skip(1).map(line ->{
            String[] fields = line.split(",");
            return new CurrencyConversionRate(fields[0], fields[1], fields[2]);
        }).forEach(conversionRates::add);
        System.out.println(conversionRates);
        return conversionRates;
    }
}
