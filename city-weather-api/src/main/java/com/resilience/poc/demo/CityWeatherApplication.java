package com.resilience.poc.demo;

import com.resilience.poc.IataCode;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.core.convert.converter.Converter;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.function.Function;

@SpringBootApplication
public class CityWeatherApplication {

    @Component
    public static class IataCodeConverter implements Converter<String, IataCode> {

        @Override
        public IataCode convert(String source) {
            return IataCode.of(source);
        }
    }

    @RestController
    public static class CityWeatherController {

        private static final String RESPONSE = "You requested the weather conditions for %s. They are the following: %s";

        private final Function<String, RestTemplate> clientBuilder = rootUri -> new RestTemplateBuilder()
                .requestCustomizers(request -> System.out.println(">>>>>>>>>> " + request.getURI()))
                .rootUri(rootUri)
                .build();

        private final RestTemplate citiesApiClient = clientBuilder.apply("http://cities:8080");
        private final RestTemplate weatherApiClient = clientBuilder.apply("http://weather:8080");

        @GetMapping("/city/{iata}/weather")
        public String getCityWeather(@PathVariable IataCode iata) {
            final String cityCode = iata.getCode();

            ResponseEntity<String> cityDetails = citiesApiClient.getForEntity("/cities/{iata}", String.class, cityCode);
            ResponseEntity<String> weatherConditions = weatherApiClient.getForEntity("/weather/{iata}", String.class, cityCode);

            return String.format(RESPONSE, cityDetails.getBody(), weatherConditions.getBody());
        }
    }

    public static void main(String[] args) {
        SpringApplication.run(CityWeatherApplication.class, args);
    }

}
