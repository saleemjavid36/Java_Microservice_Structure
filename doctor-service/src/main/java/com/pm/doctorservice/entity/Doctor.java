package com.pm.doctorservice.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Entity
@Table(
        name = "doctor_tbl",
        uniqueConstraints = {
                @UniqueConstraint(name = "unique_doctor_email",columnNames = {"email"}),
        }
)
@Data
public class Doctor {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    private String name;

    private String specialization;

    @Email
    @NotNull
    @Column(
            unique = true
    )
    private String email;
}
