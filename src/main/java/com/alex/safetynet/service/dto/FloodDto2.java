package com.alex.safetynet.service.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Builder
@Data
public class FloodDto2 {
    String address;
    List<PersonDto2> people;

    @Builder
    @Data
    public static class PersonDto2 {
        String lastName;
        String phoneNumber;
        Integer age;
        private String[] medications;
        private String[] allergies;
    }
}
