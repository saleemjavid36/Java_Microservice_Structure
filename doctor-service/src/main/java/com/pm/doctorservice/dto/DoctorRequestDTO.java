package com.pm.doctorservice.dto;

import lombok.Data;

@Data
public class DoctorRequestDTO {
    private String name;
    private String specialization;
    private String email;
}
