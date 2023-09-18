package com.klasha;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class CountryAndCurrencyApiApplication {


    public static void main(String[] args) {
        SpringApplication.run(CountryAndCurrencyApiApplication.class, args);
    }

}
