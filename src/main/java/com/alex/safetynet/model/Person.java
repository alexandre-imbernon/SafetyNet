package com.alex.safetynet.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Person {

    private String firstName;
    private String lastName;
    private String address;
    private String city;
    private String zip;
    private String phone;
    private String email;


    public Person(String firstName, String lastName, String address, String city, String zip, String phone, String email) {
        this.firstName = firstName;
        this.address = address;
        this.lastName = lastName;
        this.city = city;
        this.zip = zip;
        this.phone = phone;
        this.email = email;
    }


    public Person() {

    }
}
