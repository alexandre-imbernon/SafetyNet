package com.alex.safetynet.service.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class PersonInfoDto {
        String firstName;
        String lastName;
        String address;
        String phone;
        String email;
        int age;
        private String[] medications;
        private String[] allergies;


}

