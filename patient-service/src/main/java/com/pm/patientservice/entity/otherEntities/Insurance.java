package com.pm.patientservice.entity.otherEntities;

import com.pm.patientservice.entity.Patient;
import jakarta.persistence.*;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "insurance_tbl")
@Data
public class Insurance {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String providerName;

    @Column(unique = true)
    private String policyNumber;

    @ManyToMany(mappedBy = "insurances")
    private Set<Patient> patients = new HashSet<>();
}
