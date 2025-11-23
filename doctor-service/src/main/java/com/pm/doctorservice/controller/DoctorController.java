package com.pm.doctorservice.controller;

import com.pm.doctorservice.dto.DoctorRequestDTO;
import com.pm.doctorservice.dto.DoctorResponseDTO;
import com.pm.doctorservice.service.DoctorService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/doctors")

public class DoctorController {
    private final DoctorService doctorService;

    public DoctorController(DoctorService doctorService) {
        this.doctorService = doctorService;
    }

    @GetMapping
    public ResponseEntity<List<DoctorResponseDTO>> getAllDoctors(){
        List<DoctorResponseDTO> doctorsList = doctorService.getAllDoctors();
        return ResponseEntity.status(HttpStatus.OK).body(doctorsList);
    }
   @GetMapping("/{id}")
    public ResponseEntity<DoctorResponseDTO> getDoctorById(@PathVariable Long id){
       DoctorResponseDTO doctor = doctorService.getDocotById(id);
       return ResponseEntity.ok(doctor);
   }
   @PostMapping
    public ResponseEntity<DoctorResponseDTO> createPatient(@RequestBody DoctorRequestDTO requestDTO){
        DoctorResponseDTO doctor = doctorService.createNewDoctor(requestDTO);
        return ResponseEntity.ok(doctor);
   }


}
