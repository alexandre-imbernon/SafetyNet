package com.alex.safetynet.service.dto;

import lombok.Data;

import java.util.List;

@Data
public class FireDto {

    private String station;
    private String phoneNumber;
    private String firstName;
    private String lastName;
    private List <String> medications;
    private List <String> allergies;
    private int age;


}
