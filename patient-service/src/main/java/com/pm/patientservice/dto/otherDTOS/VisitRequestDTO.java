package com.pm.patientservice.dto.otherDTOS;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class VisitRequestDTO {
    private LocalDateTime visitDate;
    private String reason;

}
