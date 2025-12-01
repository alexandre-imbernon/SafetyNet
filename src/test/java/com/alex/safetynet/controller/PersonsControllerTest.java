package com.alex.safetynet.controller;

import com.alex.safetynet.model.MedicalRecord;
import com.alex.safetynet.model.Person;
import com.alex.safetynet.repository.FireStationRepository;
import com.alex.safetynet.repository.MedicalRecordRepository;
import com.alex.safetynet.repository.PersonRepository;
import com.alex.safetynet.service.PersonService;
import com.alex.safetynet.service.dto.ChildAlertDto;
import com.alex.safetynet.service.dto.PersonInfoDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class PersonServiceTest {

    private PersonService personService;
    private PersonRepository personRepository;
    private FireStationRepository fireStationRepository;
    private MedicalRecordRepository medicalRecordRepository;

    @BeforeEach
    void setUp() {
        // Initialiser les mocks
        personRepository = mock(PersonRepository.class);
        fireStationRepository = mock(FireStationRepository.class);
        medicalRecordRepository = mock(MedicalRecordRepository.class);

        // Initialiser le service avec les mocks
        personService = new PersonService(personRepository, fireStationRepository, medicalRecordRepository);

        // 🔹 Roger (enfant) à 1509 Culver St
        Person personRoger = new Person("Roger", "Boyd", "1509 Culver St", "Culver", "97451",
                "841-874-9888", "roger@mail.com");
        when(personRepository.findpersonByfirstNameAndLastName("Roger", "Boyd")).thenReturn(personRoger);

        MedicalRecord recordRoger = new MedicalRecord();
        recordRoger.setFirstName("Roger");
        recordRoger.setLastName("Boyd");
        recordRoger.setBirthdate("09/06/2017"); // enfant de 8 ans en 2025
        recordRoger.setAllergies(List.of("nillacilan"));
        recordRoger.setMedications(List.of("aznol:350mg"));
        when(medicalRecordRepository.findMedicalWithFirstNameAndLastName("Roger", "Boyd")).thenReturn(recordRoger);

        // 🔹 Anna (adulte) au même foyer
        Person personAnna = new Person("Anna", "Boyd", "1509 Culver St", "Culver", "97451",
                "841-874-7777", "anna@mail.com");
        MedicalRecord recordAnna = new MedicalRecord();
        recordAnna.setFirstName("Anna");
        recordAnna.setLastName("Boyd");
        recordAnna.setBirthdate("09/06/1985"); // adulte
        recordAnna.setAllergies(List.of());
        recordAnna.setMedications(List.of());
        when(medicalRecordRepository.findMedicalWithFirstNameAndLastName("Anna", "Boyd")).thenReturn(recordAnna);

        // 🔹 Mock de la liste des personnes par adresse
        when(personRepository.findAllPersonByAddress("1509 Culver St"))
                .thenReturn(Arrays.asList(personRoger, personAnna));

        // 🔹 Mock de la liste de toutes les personnes (pour findAllEmailsTest)
        List<Person> persons = Arrays.asList(
                new Person("Reginold", "Walker", "908 73rd St", "Culver", "97451",
                        "841-874-8547", "reg@email.com"),
                new Person("Jamie", "Peters", "908 73rd St", "Culver", "97451",
                        "841-874-7462", "jpeter@email.com"),
                new Person("Ron", "Peters", "112 Steppes Pl", "Culver", "97451",
                        "841-874-8888", "jpeter@email.com")
        );
        when(personRepository.findAllPersons()).thenReturn(persons);
    }

    // ------------------------ TESTS ------------------------

    @Test
    void findAllEmailsTest() {
        List<String> result = personService.findAllEmails("Culver");

        assertEquals(3, result.size());
        assertTrue(result.contains("reg@email.com"));
        assertTrue(result.contains("jpeter@email.com"));
        assertFalse(result.contains("nonexistent@email.com"));
    }

    @Test
    void listOfPersonsWithMedicalRecordsTest() {
        List<PersonInfoDto> result = personService.findAllPersonsWithMedicalRecords("Roger", "Boyd");

        assertFalse(result.isEmpty());
        assertEquals("Roger", result.get(0).getFirstName());
        assertEquals("Boyd", result.get(0).getLastName());
        assertEquals("roger@mail.com", result.get(0).getEmail());
        assertEquals(8, result.get(0).getAge()); // dépend de computeAge
        assertArrayEquals(new String[]{"aznol:350mg"}, result.get(0).getMedications());
        assertArrayEquals(new String[]{"nillacilan"}, result.get(0).getAllergies());
    }

    @Test
    void childsUnder18ByAddressTest() {
        String address = "1509 Culver St";

        List<ChildAlertDto> result = personService.findAllChildsUnder18ByAddress(address);

        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals("Roger", result.get(0).getFirstName());
        assertEquals("Boyd", result.get(0).getLastName());
        assertEquals("8", result.get(0).getAge()); // dépend de computeAge
        assertEquals(1, result.get(0).getHouseholds().size()); // Anna est listée comme membre du foyer
    }

    @Test
    void findAllPersons() {
        List<Person> result = personService.findAllPersons();
        assertEquals(3, result.size());
    }

    @Test
    void addPerson() {
        // À implémenter quand tu ajouteras la méthode correspondante
    }

    @Test
    void updatePerson() {
        // À implémenter quand tu ajouteras la méthode correspondante
    }

    @Test
    void deletePerson() {
        // À implémenter quand tu ajouteras la méthode correspondante
    }
}
