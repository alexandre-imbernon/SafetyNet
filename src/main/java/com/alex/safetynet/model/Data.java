package com.alex.safetynet.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;

@Setter
@Getter
@Component
public class Data {

    private List <Person> persons;
    private List <FireStation> firestations;
    private List <MedicalRecord> medicalrecords;
    public List<MedicalRecord> getMedicalRecords() {return medicalrecords;}
    public List<FireStation> getFireStations() {return firestations;}


    public List<FireStation> getFirestations() {return firestations;}
}
