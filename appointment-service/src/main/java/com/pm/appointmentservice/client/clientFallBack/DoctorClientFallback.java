package com.pm.appointmentservice.client.clientFallBack;

import com.pm.appointmentservice.client.DoctorClient;
import com.pm.appointmentservice.client.PatientClient;
import com.pm.appointmentservice.client.clientDTO.DoctorResponse;
import com.pm.appointmentservice.client.clientDTO.PatientResponse;
import org.springframework.stereotype.Component;

@Component
public class DoctorClientFallback implements DoctorClient {
    @Override
    public DoctorResponse getDoctorById(Long id) {
        return new DoctorResponse(id, "unknown", "unknown@example.com", null);
    }
}
