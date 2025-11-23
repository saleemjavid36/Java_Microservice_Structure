package com.pm.appointmentservice.grpc;

import com.pm.grpc.PatientRequest;
import com.pm.grpc.PatientResponse;
import com.pm.grpc.PatientServiceGrpc;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.stereotype.Service;

@Service
public class PatientGrpcClient {
    @GrpcClient("patientService")
    private PatientServiceGrpc.PatientServiceBlockingStub patientStub;

    public PatientResponse getPatient(Long id) {
        PatientRequest request = PatientRequest.newBuilder()
                .setId(id)
                .build();

        return patientStub.getPatientById(request);
    }
}
