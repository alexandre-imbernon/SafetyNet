package com.alex.safetynet.service.dto;

import lombok.Data;

import java.util.List;

@Data
public class FireStationPersonDto {
    String lastName;
    String firstName;
    String address;
    String phoneNumber;

    public FireStationPersonDto(List<PersonInfoDto> personDTOList, int adultCount, int childCount) {
    }
}