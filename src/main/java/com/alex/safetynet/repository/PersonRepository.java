package com.alex.safetynet.repository;

import com.alex.safetynet.model.Person;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.stream.Collectors;

@Component

public class PersonRepository {

    private final DataHandler dataHandler;

    public PersonRepository(DataHandler datahandler) {this.dataHandler = datahandler;}

    public List<Person> findAllPersons() {return dataHandler.getData().getPersons();}

    public List<Person> findAllPersonByAddress(String address){
        return dataHandler.getData().getPersons()
                .stream()
                .filter(p -> p.getAddress().equals(address))
                .collect(Collectors.toList());
    }

    public Person findpersonByfirstNameAndLastName(String firstName, String lastName){
        return dataHandler.getData().getPersons().stream() // Stream<Person>
                .filter(p -> p.getFirstName().equals(firstName))
                .filter(p -> p.getLastName().equals(lastName))
                .findFirst() // Optional<Person>
                .orElseGet(() -> new Person());
    }
}
