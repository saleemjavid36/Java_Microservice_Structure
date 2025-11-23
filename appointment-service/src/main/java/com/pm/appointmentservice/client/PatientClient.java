package com.pm.appointmentservice.client;


import com.pm.appointmentservice.client.clientDTO.PatientResponse;
import com.pm.appointmentservice.client.clientFallBack.PatientClientFallback;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(
        name = "PATIENT-SERVICE",path = "/api/v1/patients",
        fallback = PatientClientFallback.class
)
public interface PatientClient {
    @GetMapping("/{id}")
    PatientResponse getPatientById(@PathVariable("id") Long id);
}
