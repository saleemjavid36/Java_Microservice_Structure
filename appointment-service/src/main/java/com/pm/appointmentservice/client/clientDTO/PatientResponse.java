package com.pm.appointmentservice.client.clientDTO;

import java.time.LocalDate;

public record PatientResponse(
        Long id,
        String name,
        String email,
        LocalDate birthDate,
        String bloodGroup
) {}

