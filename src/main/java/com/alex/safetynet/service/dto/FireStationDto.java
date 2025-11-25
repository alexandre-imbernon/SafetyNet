package com.alex.safetynet.service.dto;

import lombok.Data;
import java.util.List;

@Data
public class FireStationDto {

    private int adultsCount;
    private int childsCount;
    private List<PersonDto> people;

    public FireStationDto(List<PersonDto> people, int adultsCount, int childsCount) {
        this.people = people;
        this.adultsCount = adultsCount;
        this.childsCount = childsCount;
    }
}
