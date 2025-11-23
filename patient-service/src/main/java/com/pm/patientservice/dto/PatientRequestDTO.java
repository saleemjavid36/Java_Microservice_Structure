package com.pm.patientservice.dto;

import com.pm.patientservice.entity.Patient;
import com.pm.patientservice.entity.type.BloodGroupType;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class PatientRequestDTO {
    private String name;
    private String gender;
    private LocalDate birth_date;
    private String email;
    private BloodGroupType bloodGroup;
}