package com.pm.patientservice.events;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PatientRegisteredEvent {
    private Long patientId;
    private String name;
    private String email;
}