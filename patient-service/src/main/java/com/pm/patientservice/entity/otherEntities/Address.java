package com.pm.patientservice.entity.otherEntities;

import com.pm.patientservice.entity.Patient;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "address_tbl")
@Data
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String street;
    private String city;
    private String state;
    private String zipCode;

    @OneToOne
    @JoinColumn(name = "patient_id", unique = true)
    private Patient patient;

}
