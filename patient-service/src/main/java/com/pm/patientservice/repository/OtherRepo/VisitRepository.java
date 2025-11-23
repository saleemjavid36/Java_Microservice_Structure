package com.pm.patientservice.repository.OtherRepo;

import com.pm.patientservice.entity.otherEntities.Visit;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VisitRepository extends JpaRepository<Visit, Long> {
}
