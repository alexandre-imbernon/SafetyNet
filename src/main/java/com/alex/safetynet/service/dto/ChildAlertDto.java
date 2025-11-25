package com.alex.safetynet.service.dto;

import com.alex.safetynet.model.Person;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class ChildAlertDto {
    private String firstName;
    private String lastName;
    private String age;
    private List<Person> households;

    public ChildAlertDto() {}

    public ChildAlertDto(String firstName, String lastName, String age, List<Person> households) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.households = households;
    }
}
