package com.alex.safetynet.controller;

import com.alex.safetynet.model.Data;
import com.alex.safetynet.model.FireStation;
import com.alex.safetynet.model.Person;
import com.alex.safetynet.repository.FireStationRepository;
import com.alex.safetynet.service.dto.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class FireStationsControllerTest {

    private static List<String> phoneNumbers;

    @Autowired
    private FireStationsController fireStationsController;

    @Autowired
    private FireStationRepository fireStationRepository;
    @Autowired
    private Data data;

    @BeforeAll
    void setUp() {
        phoneNumbers = new ArrayList<>();
        phoneNumbers.add("841-874-6874");
        phoneNumbers.add("841-874-9845");
        phoneNumbers.add("841-874-8888");
        phoneNumbers.add("841-874-9888");

        List<Person> households = new ArrayList<>();
        households.add(new Person("Sophia", "Zemicks", "892 Downing Ct", "Culver", "97451", "841-874-7878", "soph@email.com"));
        households.add(new Person("Warren", "Zemicks", "892 Downing Ct", "Culver", "97451", "841-874-7512", "ward@email.com"));

        ChildAlertDto childAlertDto = new ChildAlertDto("Zach", "Zemicks", "3", households);
    }

    @Test
    void phoneNumberListTests() {
        assert (fireStationsController.phoneNumberList(4).equals(phoneNumbers));
    }

    @Test
    void personsListByFireStationTest() {
        FireStationDto result = fireStationsController.personsListByFireStation(2);
        assert (result.getPeople().get(0).getFirstName().contains("Jonanathan"));
    }

    @Test
    void allFireStationsTest() {
        // WHEN - Appel du controller
        List<FireStation> result = fireStationsController.allFireStations();
        // THEN - Vérifications
        assert (!result.isEmpty());  // La liste n'est pas vide
        assert (result.get(0).getAddress() != null);  // Les adresses existent
    }

    @Test
    void foyersListByFireStation() {
        FloodDto result = fireStationsController.foyersListByFireStation(2);
        assert (result.getFoyers().get(0).getFirstName().contains("Jonanathan"));
    }

    @Test
    void flood() {
        List<FloodDto2> result = fireStationsController.flood(List.of(1, 2, 3,4));
        assertEquals("644 Gershwin Cir", result.get(0).getAddress());
        assertEquals("908 73rd St", result.get(1).getAddress());
        assertEquals("947 E. Rose Dr", result.get(2).getAddress());
        assertEquals("29 15th St", result.get(3).getAddress());
        assertEquals("892 Downing Ct", result.get(4).getAddress());
        assertEquals("951 LoneTree Rd", result.get(5).getAddress());
        assertEquals("1509 Culver St", result.get(6).getAddress());
        assertEquals("834 Binoc Ave", result.get(7).getAddress());
        assertEquals("748 Townings Dr", result.get(8).getAddress());
        assertEquals("112 Steppes Pl", result.get(9).getAddress());
        assertEquals("748 Townings Dr", result.get(10).getAddress());
        assertEquals("489 Manchester St", result.get(11).getAddress());
        assertEquals("112 Steppes Pl", result.get(12).getAddress());
    }


    @Test
    void getFireStation() {
        List<FireDto> result = fireStationsController.getFireStation("1509 Culver St");

        // On vérifie que la liste n'est pas vide
        assertFalse(result.isEmpty());

        // On récupère le premier DTO
        FireDto first = result.get(0);

        // Vérifications des champs
        assertEquals("3", first.getStation());
        assertEquals("John", first.getFirstName());
        assertEquals("Boyd", first.getLastName());
        assertEquals("841-874-6512", first.getPhoneNumber());
        assertEquals(41, first.getAge());
        assertArrayEquals(new String[]{"aznol:350mg", "hydrapermazol:100mg"}, first.getMedications().toArray());
        assertArrayEquals(new String[]{"nillacilan"}, first.getAllergies().toArray());
    }
}