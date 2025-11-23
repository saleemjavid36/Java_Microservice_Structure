package com.pm.appointmentservice.client.clientDTO;

public record DoctorResponse(
        Long id,
        String name,
        String specialization,
        String email
) {}

