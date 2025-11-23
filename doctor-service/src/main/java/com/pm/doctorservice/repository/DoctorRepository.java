package com.pm.doctorservice.repository;

import com.pm.doctorservice.dto.DoctorResponseDTO;
import com.pm.doctorservice.entity.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DoctorRepository extends JpaRepository<Doctor,Long> {
    Optional<Doctor> findByEmail(String email);
}
