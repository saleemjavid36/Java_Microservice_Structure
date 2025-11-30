package com.pm.doctorservice.service;

import com.pm.doctorservice.dto.DoctorRequestDTO;
import com.pm.doctorservice.dto.DoctorResponseDTO;
import com.pm.doctorservice.dto.MappingDtoToEntity;
import com.pm.doctorservice.entity.Doctor;
import com.pm.doctorservice.exceptionHandlers.CustomResourceConflictException;
import com.pm.doctorservice.grpc.PatientServiceGrpcClient;
import com.pm.doctorservice.grpc.PatientSpringGrpcClient;
import com.pm.doctorservice.repository.DoctorRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class DoctorService {
    private final DoctorRepository doctorRepository;
    private final PatientServiceGrpcClient patientGrpcClient;
    private final PatientSpringGrpcClient patientSpringGrpcClient;

    public DoctorService(DoctorRepository doctorRepository, PatientServiceGrpcClient patientGrpcClient, PatientSpringGrpcClient patientSpringGrpcClient) {
        this.doctorRepository = doctorRepository;
        this.patientGrpcClient = patientGrpcClient;
        this.patientSpringGrpcClient = patientSpringGrpcClient;
    }

    public List<DoctorResponseDTO> getAllDoctors() {
        List<Doctor> doctorList = doctorRepository.findAll();
//        com.pm.grpc.PatientResponse grpcResponse = patientGrpcClient.getPatientById(3L);
        com.pm.grpc.PatientResponse grpcResponse = patientSpringGrpcClient.getPatientById(4L);

        log.info("🧠 Fetched Updated Data via gRPC: {}", grpcResponse);
        return doctorList.stream()
                .map(doctor->new DoctorResponseDTO(doctor))
                .collect(Collectors.toList());
    }

    public DoctorResponseDTO getDocotById(Long id) {
        Doctor doctor = doctorRepository.findById(id).orElseThrow(
                ()->new EntityNotFoundException("Doctor Not Found with Id: "+ id)
        );
        return new DoctorResponseDTO(doctor);
    }

    public DoctorResponseDTO createNewDoctor(DoctorRequestDTO requestDTO) {
        Optional<Doctor> doctor = doctorRepository.findByEmail(requestDTO.getEmail());
        if(doctor.isPresent()){
            throw new CustomResourceConflictException("Patient with Email "+ requestDTO.getEmail()+"Already Exist");
        }
        Doctor newDoctor = MappingDtoToEntity.mapRequestDtoToPatient(requestDTO);
        Doctor savedDoctor = doctorRepository.save(newDoctor);
        return new DoctorResponseDTO(savedDoctor);
    }
}
