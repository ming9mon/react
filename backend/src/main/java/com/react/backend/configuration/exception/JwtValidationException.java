package com.react.backend.configuration.exception;

public class JwtValidationException extends RuntimeException {
    public JwtValidationException(String message) {super(message);}
}
