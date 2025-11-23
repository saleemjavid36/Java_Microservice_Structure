package com.pm.patientservice.entity.otherEntities;

import com.pm.patientservice.entity.Patient;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Table(name = "visit_tbl")
@Data
public class Visit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime visitDate;

    private String reason;

    @ManyToOne
    @JoinColumn(name = "patient_id")
    private Patient patient;

}
