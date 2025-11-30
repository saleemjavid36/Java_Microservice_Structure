package com.pm.doctorservice.grpc;

import com.pm.grpc.PatientRequest;
import com.pm.grpc.PatientResponse;
import com.pm.grpc.PatientServiceGrpc;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import net.devh.boot.grpc.client.inject.GrpcClient;

import java.util.concurrent.TimeUnit;

@Service
public class PatientSpringGrpcClient {
    private static final Logger log =
            LoggerFactory.getLogger(PatientSpringGrpcClient.class);
    @GrpcClient("patientService")
    private PatientServiceGrpc.PatientServiceBlockingStub patientStub;
    public PatientResponse getPatientById(Long id) {
        PatientRequest request = PatientRequest.newBuilder()
                .setId(id)
                .build();
        PatientResponse response = patientStub
                .withDeadlineAfter(5, TimeUnit.SECONDS)
                .getPatientById(request);
        log.info("✅ gRPC response from patient-service: {}", response);
        return response;
    }
}
