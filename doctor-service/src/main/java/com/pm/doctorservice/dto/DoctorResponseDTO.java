package com.pm.doctorservice.dto;

import com.pm.doctorservice.entity.Doctor;
import lombok.Data;

@Data
public class DoctorResponseDTO {
    private Long id;
    private String name;
    private String specialization;
    private String email;

    public DoctorResponseDTO(Doctor doctor) {
        this.id = doctor.getId();
        this.name = doctor.getName();
        this.specialization = doctor.getSpecialization();
        this.email = doctor.getEmail();
    }
}
