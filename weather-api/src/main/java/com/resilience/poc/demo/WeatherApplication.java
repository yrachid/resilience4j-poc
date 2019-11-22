package com.resilience.poc.demo;

import com.resilience.poc.IataCode;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@SpringBootApplication
public class WeatherApplication {

    @RestController
    public static class WeatherController {

        private final Map<IataCode, String> conditions;

        public WeatherController() {
            conditions = new HashMap<>();

            conditions.put(IataCode.of("POA"), "It's a shitty and unpredictable weather.");
            conditions.put(IataCode.of("SAO"), "Lot's of pollution today. Expect a grey mediocre day.");
        }

        @GetMapping("/weather/{iata}")
        public ResponseEntity<String> getWeatherConditions(@PathVariable IataCode iata) {
            return somethingFailedRandomly()
                    ? new ResponseEntity<>("Yo, things are mad", INTERNAL_SERVER_ERROR)
                    : ResponseEntity.ok(
                    conditions.getOrDefault(iata, "I can't give you any weather conditions."));
        }

        private boolean somethingFailedRandomly() {
            return Math.random() > 0.5;
        }

    }

    public static void main(String[] args) {
        SpringApplication.run(WeatherApplication.class, args);
    }

}
