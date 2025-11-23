package com.pm.patientservice.dto.otherDTOS;

import lombok.Data;

@Data
public class AddressRequestDTO {
    private String street;
    private String city;
    private String state;
    private String zipCode;

}
