package com.pm.patientservice.controller;

import com.pm.patientservice.dto.PagedResponse;
import com.pm.patientservice.dto.PatientRequestDTO;
import com.pm.patientservice.dto.PatientResponseDTO;
import com.pm.patientservice.entity.Patient;
import com.pm.patientservice.service.PatientService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//@RestController
//@RequestMapping("/patients")
public class PatientController {

    private final PatientService patientService;

    public PatientController(PatientService patientService) {
        this.patientService = patientService;
    }

    @GetMapping
    public ResponseEntity<PagedResponse<PatientResponseDTO>> getAllPatients(
//           can support like this api/v1/patients?page=0&size=10&sort=name,asc&sort=id,desc
            @PageableDefault(page = 0,size = 10,sort = "id") Pageable pageable
//            If you want default params below code
//            @RequestParam(name = "p") int page,
//            @RequestParam(name = "s") int size
    ){
//        Pageable pageable = PageRequest.of(page, size);
       Page<PatientResponseDTO> patientPage = patientService.getAllPatients(pageable);
       PagedResponse<PatientResponseDTO> response = new PagedResponse<>(patientPage);
       return ResponseEntity.ok(response);
    }

//    @GetMapping("/{id}")
//    public ResponseEntity<PatientResponseDTO> getPatientById(@PathVariable Long id){
//        ResponseEntity<PatientResponseDTO> patient = ResponseEntity.ok(patientService.getPatientById(id));
//        return patient;
//    }
//
//    @PostMapping
//    public ResponseEntity<PatientResponseDTO> createPatient(@RequestBody PatientRequestDTO requestDTO){
//        PatientResponseDTO patient = patientService.createPatient(requestDTO);
//        return ResponseEntity.status(HttpStatus.CREATED).body(patient);
//    }
}











