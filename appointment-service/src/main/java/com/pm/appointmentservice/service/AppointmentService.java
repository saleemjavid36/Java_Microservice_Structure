package com.pm.appointmentservice.service;

import com.pm.appointmentservice.client.DoctorClient;
import com.pm.appointmentservice.client.PatientClient;
import com.pm.appointmentservice.client.clientDTO.DoctorResponse;
import com.pm.appointmentservice.client.clientDTO.PatientResponse;
import com.pm.appointmentservice.dto.AppointmentResponseDTO;
import com.pm.appointmentservice.dto.CreateAppointmentRequest;
import com.pm.appointmentservice.dto.details.AppointmentDetails;
import com.pm.appointmentservice.entity.Appointment;
import com.pm.appointmentservice.repository.AppointmentRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AppointmentService {
    private final AppointmentRepository appointmentRepository;
    private final PatientClient patientClient;
    private final DoctorClient doctorClient;

    public AppointmentService(
            AppointmentRepository appointmentRepository,
            @Qualifier("com.pm.appointmentservice.client.PatientClient") PatientClient patientClient,
            @Qualifier("com.pm.appointmentservice.client.DoctorClient") DoctorClient doctorClient
    ) {
        this.appointmentRepository = appointmentRepository;
        this.patientClient = patientClient;
        this.doctorClient = doctorClient;
    }

    public List<AppointmentResponseDTO> getAllAppointments() {
        List<Appointment> appointmentList = appointmentRepository.findAll();
        return appointmentList.stream()
                .map(appointment->new AppointmentResponseDTO(appointment))
                .collect(Collectors.toList());
    }

    public AppointmentDetails getAppointmentDetails(Long appointmentId) {
        Appointment app = appointmentRepository.findById(appointmentId)
                .orElseThrow(() -> new EntityNotFoundException("Appointment not found"));

        // remote calls
        PatientResponse patient = patientClient.getPatientById(app.getPatientId());
        DoctorResponse doctor = doctorClient.getDoctorById(app.getDoctorId());
        return new AppointmentDetails(
                app.getId(),
                app.getAppointmentTime(),
                app.getReason(),
                app.getStatus(),
                patient,
                doctor
        );
    }

    public Appointment createAppointment(CreateAppointmentRequest req) {
        // validate patientId & doctorId existence optionally:
        // patientClient.getPatientById(req.patientId());

        Appointment appointment = new Appointment();
        appointment.setAppointmentTime(req.getAppointmentTime());
        appointment.setReason(req.getReason());
        appointment.setPatientId(req.getPatientId());
        appointment.setDoctorId(req.getDoctorId());
        appointment.setStatus("CREATED");

        // Directly return the result of the save operation
        return appointmentRepository.save(appointment);
    }
}
