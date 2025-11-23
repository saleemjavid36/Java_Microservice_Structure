package com.pm.appointmentservice.dto.details;

import com.pm.appointmentservice.client.clientDTO.DoctorResponse;
import com.pm.appointmentservice.client.clientDTO.PatientResponse;

import java.time.LocalDateTime;

public record AppointmentDetails(
        Long id,
        LocalDateTime appointmentTime,
        String reason,
        String status,
        PatientResponse patient,
        DoctorResponse doctor
) {
}
