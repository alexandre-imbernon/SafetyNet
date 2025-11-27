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

    public Person savePerson(Person person) {
        dataHandler.getData().getPersons().add(person);
        return person;
    }

    public Person updatePerson(Person person, String firstName, String lastName) {
        List<Person> persons = dataHandler.getData().getPersons();

        for (Person p : persons) {
            if (p.getFirstName().equals(firstName)
                    && p.getLastName().equals(lastName)) {

                // Mettre à jour tous les champs sauf firstName et lastName
           p.setFirstName(person.getFirstName());
           p.setLastName(person.getLastName());
                return p;
            }
        }

        throw new IllegalArgumentException("Person not found");
    }


    public void deletePerson(String firstName, String lastName) {
        List<Person> persons = dataHandler.getData().getPersons();

        boolean exists = persons.stream().anyMatch(p ->
                p.getFirstName().equalsIgnoreCase(firstName.trim()) &&
                        p.getLastName().equalsIgnoreCase(lastName.trim()));

        persons.removeIf(p ->
                p.getFirstName().equalsIgnoreCase(firstName.trim()) &&
                        p.getLastName().equalsIgnoreCase(lastName.trim()));

        dataHandler.save();
    }
}
