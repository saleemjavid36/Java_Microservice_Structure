package com.pm.patientservice.utils;

import com.pm.patientservice.dto.otherDTOS.InsuranceRequestDTO;
import com.pm.patientservice.dto.otherDTOS.InsuranceResponseDTO;
import com.pm.patientservice.entity.otherEntities.Insurance;
import org.springframework.stereotype.Component;

@Component
public class InsuranceMapper {

    public Insurance toEntity(InsuranceRequestDTO req) {
        Insurance i = new Insurance();
        i.setProviderName(req.getProviderName());
        i.setPolicyNumber(req.getPolicyNumber());
        return i;
    }

    public InsuranceResponseDTO toResponse(Insurance i) {
        InsuranceResponseDTO resp = new InsuranceResponseDTO();
        resp.setId(i.getId());
        resp.setProviderName(i.getProviderName());
        resp.setPolicyNumber(i.getPolicyNumber());
        return resp;
    }
}

