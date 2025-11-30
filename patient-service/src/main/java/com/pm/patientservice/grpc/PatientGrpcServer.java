package com.pm.patientservice.grpc;

import com.pm.grpc.PatientRequest;
import com.pm.grpc.PatientResponse;
import com.pm.grpc.PatientServiceGrpc;
import com.pm.patientservice.entity.Patient;
import com.pm.patientservice.repository.PatientRepository;
import io.grpc.Status;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;

@GrpcService
public class PatientGrpcServer extends PatientServiceGrpc.PatientServiceImplBase {

    final private PatientRepository patientRepository;

    public PatientGrpcServer(PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }

    @Override
    public void getPatientById(
            PatientRequest request,
            StreamObserver<PatientResponse> responseObserver
    ) {
        try {
            Patient patient = patientRepository.findById(request.getId())
                    .orElseThrow(() -> new RuntimeException("Patient not found"));

            PatientResponse response = PatientResponse.newBuilder()
                    .setId(patient.getId())
                    .setName(patient.getName())
                    .setEmail(patient.getEmail())
                    .build();

            responseObserver.onNext(response);
            responseObserver.onCompleted();

        } catch (Exception ex) {
            responseObserver.onError(
                    Status.NOT_FOUND
                            .withDescription(ex.getMessage())
                            .asRuntimeException()
            );
        }
    }
}
