package com.att.m2x.android.exceptions;

import java.util.Map;

/**
 * Used for reporting issues interacting with the M2X API.
 */
public class M2XApiException extends Exception {

    public static final String REQUEST_TIMED_OUT = "Request timed out";

    public M2XApiException() {
        super();
    }

    public M2XApiException(String message) {
        super(message);
    }

    public M2XApiException(String message, String description, Map<String, String> errors) {
        super(message);
        this.description = description;
        this.errors = errors;
    }

    public M2XApiException(Throwable t) {
        super(t);
    }

    public M2XApiException(String message, Throwable t) {
        super(message, t);
    }


    private Map<String, String> errors;
    public Map<String, String> getErrors() {
        return errors;
    }

    private String description;
    public String getDescription() {
        return description;
    }

}
