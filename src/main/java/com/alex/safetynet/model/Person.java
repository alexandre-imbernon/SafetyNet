package com.alex.safetynet.model;

import lombok.Getter;

@Getter
public class Person {

    private String firstName;
    private String lastName;
    private String address;
    private String city;
    private String zip;
    private String phone;
    private String email;

    public Person() {
    }

    public Person(String firstName, String address, String lastName, String city, String zip, String phone, String email) {
        this.firstName = firstName;
        this.address = address;
        this.lastName = lastName;
        this.city = city;
        this.zip = zip;
        this.phone = phone;
        this.email = email;
    }
}
