package com.pm.patientservice.exceptionHandlers;

import java.time.LocalDateTime;

public class ErrorDetails {
    private int status;
    private String message;
//    private LocalDateTime timestamp;
    private long timestamp;

    public ErrorDetails(int status, String message ,long timestamp) {
        this.status = status;
        this.message = message;
//        this.timestamp = LocalDateTime.now();
        this.timestamp = timestamp;
    }


}
