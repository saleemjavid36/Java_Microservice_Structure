package com.pm.patientservice.repository.OtherRepo;

import com.pm.patientservice.entity.otherEntities.Insurance;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InsuranceRepository extends JpaRepository<Insurance, Long> {
}
