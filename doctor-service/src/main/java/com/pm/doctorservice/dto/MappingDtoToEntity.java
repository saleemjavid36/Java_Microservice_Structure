package com.pm.doctorservice.dto;

import com.pm.doctorservice.entity.Doctor;

public class MappingDtoToEntity {
    public static Doctor mapRequestDtoToPatient(DoctorRequestDTO requestDTO){
        Doctor doctor = new Doctor();
        doctor.setName(requestDTO.getName());
        doctor.setEmail(requestDTO.getEmail());
        doctor.setSpecialization(requestDTO.getSpecialization());
        return doctor;
    }
}
