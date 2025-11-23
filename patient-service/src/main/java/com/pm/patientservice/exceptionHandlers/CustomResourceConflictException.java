package com.pm.patientservice.exceptionHandlers;

import org.springframework.http.HttpStatus;

public class CustomResourceConflictException extends RuntimeException {
    // You can optionally add a constructor that accepts a message
    public CustomResourceConflictException(String message) {
        super(message);
    }

    // You can optionally add a constructor that accepts a message and cause
    public CustomResourceConflictException(String message, Throwable cause) {
        super(message, cause);
    }
}
