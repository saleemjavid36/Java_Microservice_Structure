package com.pm.doctorservice.grpc;

import com.pm.grpc.PatientRequest;
import com.pm.grpc.PatientResponse;
import com.pm.grpc.PatientServiceGrpc;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.StatusRuntimeException;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class PatientServiceGrpcClient {
    private static final Logger log = LoggerFactory.getLogger(PatientServiceGrpcClient.class);

    // The synchronous blocking stub for making calls
    private PatientServiceGrpc.PatientServiceBlockingStub blockingStub;

    /**
     * Initializes the gRPC channel and the blocking stub.
     * Uses Spring @Value to inject configurable server address and port.
     */
//    @Value("${patient.service.address:localhost}")
//    private String serverAddress;
//
//    @Value("${patient.service.grpc.port:9009}")
//    private int serverPort;
//
//    @PostConstruct
//    public void init() {
//        log.info("Initializing gRPC client to {}:{}", serverAddress, serverPort);
//        ManagedChannel channel = ManagedChannelBuilder.forAddress(serverAddress, serverPort)
//                .usePlaintext()
//                .build();
//        blockingStub = PatientServiceGrpc.newBlockingStub(channel);
//        log.info("gRPC client initialized successfully");
//    }
    public PatientServiceGrpcClient(
            @Value("${patient.service.address:localhost}") String serverAddress,
            @Value("${patient.service.grpc.port:9009}") int serverPort // Assuming 9091 based on your previous logs
    ) {
        log.info("Initializing gRPC client to {}:{}", serverAddress, serverPort);

        // 1. Create a ManagedChannel
        ManagedChannel channel = ManagedChannelBuilder.forAddress(serverAddress, serverPort)
                .usePlaintext()
                .build();

        // 2. Create the Blocking Stub using the channel
        blockingStub = PatientServiceGrpc.newBlockingStub(channel);
        log.info("gRPC client initialized successfully");
    }

    /**
     * Calls the PatientService gRPC server to retrieve patient details by ID.
     */
    public PatientResponse getPatientById(Long id) {

        PatientRequest request = PatientRequest.newBuilder()
                .setId(id)
                .build();
        try {
            // Set a timeout for the call (Good practice)
            PatientResponse response = blockingStub
                    .withDeadlineAfter(5, TimeUnit.SECONDS)
                    .getPatientById(request);

            log.info("Received response from patient service via gRPC: {}", response);
            return response;

        } catch (StatusRuntimeException ex) {
            // Re-throw specific exceptions for cleaner client-side handling
            if (ex.getStatus().getCode() == io.grpc.Status.Code.NOT_FOUND) {
                throw new RuntimeException("Patient not found via gRPC: " + ex.getMessage(), ex);
            }
            // Catch other communication issues (e.g., UNAVAILABLE, DEADLINE_EXCEEDED)
            throw new RuntimeException("gRPC communication failure: " + ex.getMessage(), ex);
        }
    }
}
