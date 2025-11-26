package com.alex.safetynet.service.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PersonDto {
    String firstName;
    String lastName;
    String address;
    String phone;
    int age;

    
}

