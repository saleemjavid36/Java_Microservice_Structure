package com.pm.appointmentservice.dto;

import com.pm.appointmentservice.entity.Appointment;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class AppointmentResponseDTO {
    private Long id;
    private LocalDateTime appointmentTime;
    private String reason;
    public AppointmentResponseDTO(Appointment appointment) {
        this.id = appointment.getId();
        this.appointmentTime = appointment.getAppointmentTime();
        this.reason = appointment.getReason();
    }
}
