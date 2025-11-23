package com.pm.appointmentservice.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;


@Entity
@Data
@Table(name = "appointment_tbl")
public class Appointment {
    @Id
    @SequenceGenerator(
            name = "appointment_seq",
            sequenceName = "appointment_seq",
            allocationSize = 1
    )
    @GeneratedValue(strategy = GenerationType.IDENTITY,generator = "appointment_seq")
    private Long id;

    @Column(nullable = false)
    private LocalDateTime appointmentTime;

    @Column(length = 500)
    private String reason;

    private Long patientId;
    private Long doctorId;

    private  String status;

}
