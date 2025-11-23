package com.pm.patientservice.repository.OtherRepo;

import com.pm.patientservice.entity.otherEntities.Address;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<Address, Long> {
}
