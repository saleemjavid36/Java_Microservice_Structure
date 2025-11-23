package com.pm.patientservice.dto;

import com.pm.patientservice.dto.otherDTOS.AddressResponseDTO;
import com.pm.patientservice.dto.otherDTOS.InsuranceResponseDTO;
import com.pm.patientservice.dto.otherDTOS.VisitResponseDTO;
import com.pm.patientservice.entity.Patient;
import com.pm.patientservice.entity.type.BloodGroupType;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Data
@NoArgsConstructor
public class PatientResponseDTO {
    private Long id;
    private String name;
    private String gender;
    private LocalDate birth_date;
    private String email;
    private BloodGroupType bloodGroup;
    private LocalDateTime createdAt;

    private AddressResponseDTO address;
    private List<VisitResponseDTO> visits;
    private Set<InsuranceResponseDTO> insurances;

    public PatientResponseDTO(Patient patient) {
        this.id = patient.getId();
        this.name = patient.getName();
        this.gender = patient.getGender();
        this.birth_date = patient.getBirth_date();
        this.email = patient.getEmail();
        this.bloodGroup = patient.getBloodGroup();
        this.createdAt = patient.getCreatedAt();
    }
}
