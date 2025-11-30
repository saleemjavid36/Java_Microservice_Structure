package com.pm.appointmentservice.grpc;

import com.pm.grpc.PatientRequest;
import com.pm.grpc.PatientResponse;
import com.pm.grpc.PatientServiceGrpc;
import io.grpc.Status;
import io.grpc.StatusRuntimeException;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class PatientGrpcClient {
    @GrpcClient("patientService")
    private PatientServiceGrpc.PatientServiceBlockingStub patientStub;

    public PatientResponse getPatient(Long id) {

        PatientRequest request = PatientRequest.newBuilder()
                .setId(id)
                .build();

        try {
            return patientStub
                    .withDeadlineAfter(3, TimeUnit.SECONDS) // timeout
                    .getPatientById(request);

        } catch (StatusRuntimeException ex) {
            if (ex.getStatus().getCode() == Status.Code.NOT_FOUND) {
                throw new RuntimeException("Patient not found");
            }

            throw new RuntimeException("gRPC communication failure: " + ex.getMessage());
        }
    }
}
