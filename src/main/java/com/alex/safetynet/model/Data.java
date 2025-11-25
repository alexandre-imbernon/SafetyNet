package com.alex.safetynet.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;
import java.util.List;

@Setter
@Getter
@Component
public class Data {

    private List <Person> persons;
    private List <FireStation> firestations;
    private List <MedicalRecord> medicalrecords;

    public List<Person> getPersons() {return persons;}

    public List<FireStation> getFirestations() {return firestations;}

    public void setFirestations(List<FireStation> firestations) {this.firestations = firestations;}

    public List<MedicalRecord> getMedicalRecords() {return medicalrecords;}
}
