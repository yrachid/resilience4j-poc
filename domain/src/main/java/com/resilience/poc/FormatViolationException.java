package com.resilience.poc;

public class FormatViolationException extends IllegalArgumentException {

    public FormatViolationException(String s) {
        super(s);
    }
}
