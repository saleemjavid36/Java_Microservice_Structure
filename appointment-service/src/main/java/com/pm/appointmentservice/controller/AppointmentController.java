package com.pm.appointmentservice.controller;

import com.pm.appointmentservice.dto.AppointmentResponseDTO;
import com.pm.appointmentservice.dto.CreateAppointmentRequest;
import com.pm.appointmentservice.dto.details.AppointmentDetails;
import com.pm.appointmentservice.entity.Appointment;
import com.pm.appointmentservice.service.AppointmentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/appointments")
public class AppointmentController {

    private final AppointmentService appointmentService;

    public AppointmentController(AppointmentService appointmentService) {
        this.appointmentService = appointmentService;
    }

    @GetMapping
    public ResponseEntity<List<AppointmentResponseDTO>> getAllAppointments(){
        List<AppointmentResponseDTO> doctorsList = appointmentService.getAllAppointments();
        return ResponseEntity.status(HttpStatus.OK).body(doctorsList);
    }
    @GetMapping("/{id}")
    public ResponseEntity<AppointmentDetails> getAppointmentDetails(@PathVariable Long id){
        return ResponseEntity.ok(appointmentService.getAppointmentDetails(id));
    }
    @PostMapping
    public ResponseEntity<Appointment> createAppointment(@RequestBody CreateAppointmentRequest req){
        return ResponseEntity.ok(appointmentService.createAppointment(req));
    }
}











