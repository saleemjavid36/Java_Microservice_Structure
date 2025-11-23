package com.pm.patientservice.dto.otherDTOS;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class VisitResponseDTO {
    private Long id;
    private LocalDateTime visitDate;
    private String reason;
}
