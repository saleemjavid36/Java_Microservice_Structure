package com.pm.patientservice.utils;

import com.pm.patientservice.dto.otherDTOS.AddressRequestDTO;
import com.pm.patientservice.dto.otherDTOS.AddressResponseDTO;
import com.pm.patientservice.entity.otherEntities.Address;
import org.springframework.stereotype.Component;

@Component
public class AddressMapper {

    public Address toEntity(AddressRequestDTO req) {
        Address a = new Address();
        a.setStreet(req.getStreet());
        a.setCity(req.getCity());
        a.setState(req.getState());
        a.setZipCode(req.getZipCode());
        return a;
    }

    public AddressResponseDTO toResponse(Address a) {
        AddressResponseDTO resp = new AddressResponseDTO();
        resp.setId(a.getId());
        resp.setStreet(a.getStreet());
        resp.setCity(a.getCity());
        resp.setState(a.getState());
        resp.setZipCode(a.getZipCode());
        return resp;
    }
}

