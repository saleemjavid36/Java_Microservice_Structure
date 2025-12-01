package com.pm.apigateway.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
public class FallbackController {

    @GetMapping("/fallback/patient")
    public ResponseEntity<String> patientFallback() {
        return ResponseEntity.ok("Patient service temporarily unavailable 🚑");
    }

    @GetMapping("/fallback/appointment")
    public ResponseEntity<String> appointmentFallback() {
        return ResponseEntity.ok("Appointment service temporarily unavailable 🚑");
    }

    @GetMapping("/fallback/doctor")
    public ResponseEntity<String> doctorFallback() {
        return ResponseEntity.ok("Doctor service temporarily unavailable 🚑");
    }
}
