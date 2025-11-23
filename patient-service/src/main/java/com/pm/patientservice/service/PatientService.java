package com.pm.patientservice.service;

import com.pm.patientservice.dto.MappingDtoToEntity;
import com.pm.patientservice.dto.PagedResponse;
import com.pm.patientservice.dto.PatientRequestDTO;
import com.pm.patientservice.dto.PatientResponseDTO;
import com.pm.patientservice.entity.Patient;
import com.pm.patientservice.exceptionHandlers.CustomResourceConflictException;
import com.pm.patientservice.repository.PatientRepository;
import jakarta.persistence.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PatientService {
    private final PatientRepository patientRepository;
    private final ModelMapper modelMapper;

    public PatientService(PatientRepository patientRepository, ModelMapper modelMapper) {
        this.patientRepository =patientRepository;
        this.modelMapper = modelMapper;
    }

    public Page<PatientResponseDTO> getAllPatients(Pageable pageable) {
        Page<Patient> patientPage = patientRepository.findAll(pageable);
        return patientPage.map(patient ->new PatientResponseDTO(patient));
//        return patientPage.getContent()
//                .stream()
////                .map(PatientResponseDTO::new)
//                .map(patient -> new PatientResponseDTO(patient))
//                .collect(Collectors.toList());

    }

    public PatientResponseDTO getPatientById(Long id) {
        Patient patient = patientRepository.findById(id).orElseThrow(
                ()->new EntityNotFoundException("Patient not Found by id: " + id)
        );
//        return modelMapper.map(patient,PatientResponseDTO.class);
        return new PatientResponseDTO(patient);
    }

    public PatientResponseDTO createPatient(PatientRequestDTO requestDTO) {
        Optional<Patient> existingPatient = patientRepository.findByEmail(requestDTO.getEmail());
        if(existingPatient.isPresent()){
            // Custom Exception Handler
            throw new CustomResourceConflictException(
                    "Patient with email " + requestDTO.getEmail() + "already exists...!"
            );
        }
        Patient newPatient = MappingDtoToEntity.mapRequestDtoToPatient(requestDTO);
        Patient savedPatient =  patientRepository.save(newPatient);
        return new PatientResponseDTO(savedPatient);
    }
}
