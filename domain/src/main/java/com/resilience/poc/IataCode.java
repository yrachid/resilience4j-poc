package com.resilience.poc;

import lombok.Value;

@Value
public class IataCode {
    String code;

    public static IataCode of(String source) {

        if (source == null) {
            throw new FormatViolationException("Invalid IATA code: null");
        }

        if (!source.matches("[A-Z]{3}")) {
            throw new FormatViolationException("Invalid IATA code: " + source);
        }
        return new IataCode(source);
    }
}
