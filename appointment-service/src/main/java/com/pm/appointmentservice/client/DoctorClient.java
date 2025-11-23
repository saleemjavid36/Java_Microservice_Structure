package com.pm.appointmentservice.client;

import com.pm.appointmentservice.client.clientDTO.DoctorResponse;
import com.pm.appointmentservice.client.clientFallBack.DoctorClientFallback;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "doctor-service", path = "/api/v1/doctors",
        fallback = DoctorClientFallback.class)
public interface DoctorClient {
    @GetMapping("/{id}")
    DoctorResponse getDoctorById(@PathVariable("id") Long id);
}
