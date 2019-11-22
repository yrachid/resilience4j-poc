package com.resilience.poc.demo;

import com.resilience.poc.IataCode;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@SpringBootApplication
public class DemoApplication {

    @RestController
    public static final class CityController {

        private final Map<String, String> cities;

        public CityController() {
            cities = new HashMap<>();

            cities.put("POA", "Porto Alegre");
            cities.put("SAO", "São Paulo");
        }

        @GetMapping("/cities/{iata}")
        public String getCityByIata(@PathVariable IataCode iata) {
            return cities.getOrDefault(iata.getCode(), "Unknown Location");
        }

    }

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

}
