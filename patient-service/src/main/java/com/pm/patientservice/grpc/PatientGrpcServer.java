package com.pm.patientservice.grpc;

import com.pm.grpc.PatientRequest;
import com.pm.grpc.PatientResponse;
import com.pm.grpc.PatientServiceGrpc;
import com.pm.patientservice.entity.Patient;
import com.pm.patientservice.repository.PatientRepository;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;
import org.springframework.beans.factory.annotation.Autowired;

@GrpcService
public class PatientGrpcServer extends PatientServiceGrpc.PatientServiceImplBase {
    @Autowired
    private PatientRepository patientRepository;

    @Override
    public void getPatientById(PatientRequest request,
                               StreamObserver<PatientResponse> responseObserver) {

        Patient p = patientRepository.findById(request.getId())
                .orElseThrow(() -> new RuntimeException("Patient not found"));

        PatientResponse response = PatientResponse.newBuilder()
                .setId(p.getId())
                .setName(p.getName())
                .setEmail(p.getEmail())
                .build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }
}
