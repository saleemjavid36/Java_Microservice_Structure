package com.pm.patientservice.entity;


import com.pm.patientservice.entity.otherEntities.Address;
import com.pm.patientservice.entity.otherEntities.Insurance;
import com.pm.patientservice.entity.otherEntities.Visit;
import com.pm.patientservice.entity.type.BloodGroupType;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Entity
@Data
@Table(
        name = "patient_tbl",
        uniqueConstraints = {
                @UniqueConstraint(name = "unique_patient_email", columnNames = {"email"}),
                @UniqueConstraint(name = "unique_patient_name_dateOfBirth", columnNames = {"name"})

        },
        indexes = {
                @Index(
                        name = "idx_patient_date_of_birth", columnList = "birthDate"
                )
        }

)
public class Patient {
    @Id
    @SequenceGenerator(
            name = "patient_seq",
            sequenceName = "patient_seq",
            allocationSize = 1
    )
    @GeneratedValue(strategy = GenerationType.IDENTITY,generator = "patient_seq")
    private Long id;

    @NotNull
    private String name;
    private String gender;
    private LocalDate birth_date;
    //@NotNull
    //@Email
    @Column(unique = true, nullable = false)
    private String email;

    @Enumerated(EnumType.STRING)
    private BloodGroupType bloodGroup;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdAt;

    // ONE TO ONE
    @OneToOne(mappedBy = "patient", cascade = CascadeType.ALL)
    private Address address;

    // ONE TO MANY
    @OneToMany(mappedBy = "patient", cascade = CascadeType.ALL)
    private List<Visit> visits = new ArrayList<>();

    // MANY TO MANY
    @ManyToMany
    @JoinTable(
            name = "patient_insurance_tbl",
            joinColumns = @JoinColumn(name = "patient_id"),
            inverseJoinColumns = @JoinColumn(name = "insurance_id")
    )
    private Set<Insurance> insurances = new HashSet<>();
}
