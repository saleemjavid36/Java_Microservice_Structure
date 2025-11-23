package com.pm.patientservice.dto.otherDTOS;

import lombok.Data;

@Data
public class AddressResponseDTO {
    private Long id;
    private String street;
    private String city;
    private String state;
    private String zipCode;
}
