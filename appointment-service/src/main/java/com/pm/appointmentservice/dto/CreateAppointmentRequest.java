package com.pm.appointmentservice.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CreateAppointmentRequest {

    private LocalDateTime appointmentTime;
    private String reason;
    private Long patientId;
    private Long doctorId;
    private  String status;
}
