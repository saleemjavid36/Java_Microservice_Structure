package com.pm.patientservice.dto;

import com.pm.patientservice.entity.Patient;

public class MappingDtoToEntity {
    public static Patient mapRequestDtoToPatient(PatientRequestDTO requestDTO){
        Patient patient = new Patient();
        patient.setName(requestDTO.getName());
        patient.setEmail(requestDTO.getEmail());
        patient.setGender(requestDTO.getGender());
        patient.setBirth_date(requestDTO.getBirth_date());
        patient.setBloodGroup(requestDTO.getBloodGroup());
        // Do NOT set the ID here (it's auto-generated)
        return patient;
    }
}
