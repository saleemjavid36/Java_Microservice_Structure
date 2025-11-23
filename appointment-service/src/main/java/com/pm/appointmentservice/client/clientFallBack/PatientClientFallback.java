package com.pm.appointmentservice.client.clientFallBack;

import com.pm.appointmentservice.client.PatientClient;
import com.pm.appointmentservice.client.clientDTO.PatientResponse;
import org.springframework.stereotype.Component;

@Component
public class PatientClientFallback implements PatientClient {
    @Override
    public PatientResponse getPatientById(Long id) {
        // return minimal info or throw custom exception
        return new PatientResponse(id, "unknown", "unknown@example.com", null, null);
    }
}
