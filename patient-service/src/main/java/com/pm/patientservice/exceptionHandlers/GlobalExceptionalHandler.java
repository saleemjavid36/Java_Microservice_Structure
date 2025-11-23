package com.pm.patientservice.exceptionHandlers;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionalHandler {
    // --- 404 NOT FOUND Error Handling ---
    // Example: Handles exceptions like the custom EntityNotFoundException you used earlier.
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ErrorDetails> handleResourceNotFoundException(EntityNotFoundException ex) {
        ErrorDetails errorDetails = new ErrorDetails(
                HttpStatus.NOT_FOUND.value(),
                ex.getMessage(),
                System.currentTimeMillis()
        );
        return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND); // Returns 404
    }

    // --- 409 CONFLICT Error Handling ---
    // Example: Handles your check for duplicate patients (ResourceConflictException)
    @ExceptionHandler(CustomResourceConflictException.class)
    public ResponseEntity<ErrorDetails> handleResourceConflictException(CustomResourceConflictException ex) {
        ErrorDetails errorDetails = new ErrorDetails(
                HttpStatus.CONFLICT.value(),
                ex.getMessage(),
                System.currentTimeMillis()
        );
        return new ResponseEntity<>(errorDetails, HttpStatus.CONFLICT); // Returns 409
    }

    // Inside GlobalExceptionHandler.java
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ErrorDetails> handleDataIntegrityViolation(DataIntegrityViolationException ex) {
        // You must inspect the exception message here to determine the specific cause.
        String message = "Database integrity error. Check unique keys or relationships.";

        // You can check if the message contains "patient_tbl_pkey" for specific handling
        if (ex.getMessage() != null && ex.getMessage().contains("patient_tbl_pkey")) {
            message = "Failed to create patient: ID conflict.";
        }

        ErrorDetails errorDetails = new ErrorDetails(
                HttpStatus.BAD_REQUEST.value(), // Usually 400 for bad data
                message,
                System.currentTimeMillis()
        );
        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST); // Returns 400
    }

    // --- 500 INTERNAL SERVER Error Handling ---
    // A general handler for all other unhandled exceptions (e.g., unexpected null pointers, database connection failure)
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDetails> handleGlobalException(Exception ex) {
        ErrorDetails errorDetails = new ErrorDetails(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "An unexpected error occurred: " + ex.getMessage(),
                System.currentTimeMillis()
        );
        return new ResponseEntity<>(errorDetails, HttpStatus.INTERNAL_SERVER_ERROR); // Returns 500
    }

    //    Some other JWT Exceptions
//    @ExceptionHandler(AuthenticationException.class)
//    public ResponseEntity<ErrorDetails> handleAuthenticationException(AuthenticationException ex) {
//        ErrorDetails apiError = new ErrorDetails("Authentication failed: " + ex.getMessage(), HttpStatus.UNAUTHORIZED);
//        return new ResponseEntity<>(apiError, HttpStatus.UNAUTHORIZED);
//    }
//
//    @ExceptionHandler(JwtException.class)
//    public ResponseEntity<ErrorDetails> handleJwtException(JwtException ex) {
//        ErrorDetails apiError = new ErrorDetails("Invalid JWT token: " + ex.getMessage(), HttpStatus.UNAUTHORIZED);
//        return new ResponseEntity<>(apiError, HttpStatus.UNAUTHORIZED);
//    }
//
//    @ExceptionHandler(AccessDeniedException.class)
//    public ResponseEntity<ErrorDetails> handleAccessDeniedException(AccessDeniedException ex) {
//        ErrorDetails apiError = new ErrorDetails("Access denied: Insufficient permissions", HttpStatus.FORBIDDEN);
//        return new ResponseEntity<>(apiError, HttpStatus.FORBIDDEN);
//    }
}
