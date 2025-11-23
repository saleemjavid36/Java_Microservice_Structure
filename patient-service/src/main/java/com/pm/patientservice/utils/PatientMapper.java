package com.pm.patientservice.utils;

import com.pm.patientservice.dto.PatientRequestDTO;
import com.pm.patientservice.dto.PatientResponseDTO;
import com.pm.patientservice.entity.Patient;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class PatientMapper {

    private final AddressMapper addressMapper;
    private final VisitMapper visitMapper;
    private final InsuranceMapper insuranceMapper;

    public PatientMapper(AddressMapper addressMapper, VisitMapper visitMapper, InsuranceMapper insuranceMapper) {
        this.addressMapper = addressMapper;
        this.visitMapper = visitMapper;
        this.insuranceMapper = insuranceMapper;
    }

    // Convert Request DTO → Entity
    public Patient toEntity(PatientRequestDTO request) {
        Patient p = new Patient();
        p.setName(request.getName());
        p.setEmail(request.getEmail());
        p.setGender(request.getGender());
        p.setBirth_date(request.getBirth_date());
        return p;
    }

    // Convert Entity → Response DTO
    public PatientResponseDTO toResponse(Patient patient) {
        PatientResponseDTO resp = new PatientResponseDTO();
        resp.setId(patient.getId());
        resp.setName(patient.getName());
        resp.setEmail(patient.getEmail());
        resp.setGender(patient.getGender());
        resp.setBirth_date(patient.getBirth_date());
        resp.setCreatedAt(patient.getCreatedAt());

        if (patient.getAddress() != null) {
            resp.setAddress(addressMapper.toResponse(patient.getAddress()));
        }

        if (patient.getVisits() != null) {
            resp.setVisits(patient.getVisits().stream()
                    .map(visitMapper::toResponse)
                    .toList());
        }

        if (patient.getInsurances() != null) {
            resp.setInsurances(patient.getInsurances().stream()
                    .map(insuranceMapper::toResponse)
                    .collect(Collectors.toSet()));
        }

        return resp;
    }
}

