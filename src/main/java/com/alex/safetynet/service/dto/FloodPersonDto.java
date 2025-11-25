package com.alex.safetynet.service.dto;

import com.alex.safetynet.model.Person;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class FloodPersonDto {

        String firstName;
        String lastName;
        String phone;
        int age;
        private String[] medications;
        private String[] allergies;

}
