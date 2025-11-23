package com.pm.patientservice.utils;

import com.pm.patientservice.dto.otherDTOS.VisitRequestDTO;
import com.pm.patientservice.dto.otherDTOS.VisitResponseDTO;
import com.pm.patientservice.entity.otherEntities.Visit;
import org.springframework.stereotype.Component;

@Component
public class VisitMapper {

    public Visit toEntity(VisitRequestDTO req) {
        Visit v = new Visit();
        v.setVisitDate(req.getVisitDate());
        v.setReason(req.getReason());
        return v;
    }

    public VisitResponseDTO toResponse(Visit v) {
        VisitResponseDTO resp = new VisitResponseDTO();
        resp.setId(v.getId());
        resp.setVisitDate(v.getVisitDate());
        resp.setReason(v.getReason());
        return resp;
    }
}

