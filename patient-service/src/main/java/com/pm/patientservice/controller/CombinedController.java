package com.pm.patientservice.controller;

//import com.pm.commonevent.events.PatientRegisteredEvent;
import com.pm.commonevent.events.PatientRegisteredEvent;
import com.pm.patientservice.dto.PatientRequestDTO;
import com.pm.patientservice.dto.PatientResponseDTO;
import com.pm.patientservice.dto.otherDTOS.*;
import com.pm.patientservice.entity.Patient;
import com.pm.patientservice.entity.otherEntities.Address;
import com.pm.patientservice.entity.otherEntities.Insurance;
import com.pm.patientservice.entity.otherEntities.Visit;
import com.pm.patientservice.kafaka.PatientProducer;
import com.pm.patientservice.repository.OtherRepo.AddressRepository;
import com.pm.patientservice.repository.OtherRepo.InsuranceRepository;
import com.pm.patientservice.repository.OtherRepo.VisitRepository;
import com.pm.patientservice.repository.PatientRepository;
import com.pm.patientservice.utils.AddressMapper;
import com.pm.patientservice.utils.InsuranceMapper;
import com.pm.patientservice.utils.PatientMapper;
import com.pm.patientservice.utils.VisitMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/patients")
public class CombinedController {
    private final PatientRepository patientRepo;
    private final AddressRepository addressRepo;
    private final VisitRepository visitRepo;
    private final InsuranceRepository insuranceRepo;

    private final PatientMapper patientMapper;
    private final AddressMapper addressMapper;
    private final VisitMapper visitMapper;
    private final InsuranceMapper insuranceMapper;

    private final PatientProducer patientProducer;

    public CombinedController(
            PatientRepository patientRepo,
            AddressRepository addressRepo,
            VisitRepository visitRepo,
            InsuranceRepository insuranceRepo,
            PatientMapper patientMapper,
            AddressMapper addressMapper,
            VisitMapper visitMapper,
            InsuranceMapper insuranceMapper,
            PatientProducer patientProducer
    ) {
        this.patientRepo = patientRepo;
        this.addressRepo = addressRepo;
        this.visitRepo = visitRepo;
        this.insuranceRepo = insuranceRepo;
        this.patientMapper = patientMapper;
        this.addressMapper = addressMapper;
        this.visitMapper = visitMapper;
        this.insuranceMapper = insuranceMapper;
        this.patientProducer = patientProducer;
    }

    // CREATE PATIENT
    @PostMapping
    public ResponseEntity<PatientResponseDTO> create(@RequestBody PatientRequestDTO req) {
        Patient p = patientMapper.toEntity(req);
        Patient saved = patientRepo.save(p);
        return ResponseEntity.ok(patientMapper.toResponse(saved));
    }

    // GET PATIENT WITH ALL RELATIONS
    @GetMapping("/{id}")
    public ResponseEntity<PatientResponseDTO> getById(@PathVariable Long id) {
        Patient p = patientRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Patient not found"));
        com.pm.commonevent.events.PatientRegisteredEvent event =
                new PatientRegisteredEvent(p.getId(),p.getName(),p.getEmail());
        patientProducer.sendPatientRegisteredEvent(event);
        return ResponseEntity.ok(patientMapper.toResponse(p));
    }

    // ADD ADDRESS (One-to-One)
    @PostMapping("/{id}/address")
    public ResponseEntity<AddressResponseDTO> addAddress(
            @PathVariable Long id,
            @RequestBody AddressRequestDTO req
    ) {
        Patient p = patientRepo.findById(id).orElseThrow();
        Address a = addressMapper.toEntity(req);
        a.setPatient(p);
        addressRepo.save(a);
        return ResponseEntity.ok(addressMapper.toResponse(a));
    }

    // ADD VISIT
    @PostMapping("/{id}/visits")
    public ResponseEntity<VisitResponseDTO> addVisit(@PathVariable Long id,
                                                     @RequestBody VisitRequestDTO req) {
        Patient p = patientRepo.findById(id).orElseThrow();

        Visit v = visitMapper.toEntity(req);
        v.setPatient(p);
        visitRepo.save(v);

        return ResponseEntity.ok(visitMapper.toResponse(v));
    }

    // ADD INSURANCE
    @PostMapping("/insurance")
    public ResponseEntity<InsuranceResponseDTO> createInsurance(@RequestBody InsuranceRequestDTO req) {
        Insurance ins = insuranceMapper.toEntity(req);
        insuranceRepo.save(ins);
        return ResponseEntity.ok(insuranceMapper.toResponse(ins));
    }

    // ASSIGN INSURANCE TO PATIENT
    @PostMapping("/{id}/insurance/{insuranceId}")
    public ResponseEntity<String> assignInsurance(
            @PathVariable Long id,
            @PathVariable Long insuranceId) {

        Patient p = patientRepo.findById(id).orElseThrow();
        Insurance ins = insuranceRepo.findById(insuranceId).orElseThrow();

        p.getInsurances().add(ins);
        patientRepo.save(p);

        return ResponseEntity.ok("Insurance assigned to patient");
    }

}
