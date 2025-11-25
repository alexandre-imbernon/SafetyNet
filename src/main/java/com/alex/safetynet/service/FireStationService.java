package com.alex.safetynet.service;

import com.alex.safetynet.model.FireStation;
import com.alex.safetynet.model.MedicalRecord;
import com.alex.safetynet.model.Person;
import com.alex.safetynet.repository.FireStationRepository;
import com.alex.safetynet.repository.MedicalRecordRepository;
import com.alex.safetynet.repository.PersonRepository;
import com.alex.safetynet.service.dto.FireStationDto;
import com.alex.safetynet.service.dto.FloodDto;
import com.alex.safetynet.service.dto.FloodPersonDto;
import com.alex.safetynet.service.dto.PersonDto;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@Service
public class FireStationService {

    private final FireStationRepository fireStationRepository;
    private final PersonRepository personRepository;
    private final PersonService personService;
    private final MedicalRecordRepository medicalRecordRepository;

    public FireStationService(FireStationRepository fireStationRepository,
                              PersonRepository personRepository,
                              PersonService personService,
                              MedicalRecordRepository medicalRecordRepository) {
    this.fireStationRepository = fireStationRepository;
    this.personRepository = personRepository;
    this.personService = personService;
    this.medicalRecordRepository = medicalRecordRepository;
    }


    public List<FireStation> allFireStations() {
        return fireStationRepository.findAllFireStations();
    }

    // Récupérer une liste de numéros de téléphone par numéro de station
    public List<String> findPhoneNumbersByStationNumber(int number) {
        List<String> result = new ArrayList<>();
        List<FireStation> fireStations = fireStationRepository.findAllFireStationsByNumber(number);
        List<Person> persons = personRepository.findAllPersons();

        for (Person person : persons) {
            if (personsContainsFirestationAddress(fireStations, person)) {
                result.add(person.getPhone());
            }
        }
        return result;
    }

    // Vérifie si l'adresse de la personne correspond à une station
    private boolean personsContainsFirestationAddress(List<FireStation> fireStations, Person person) {
        for (FireStation fireStation : fireStations) {
            if (fireStation.getAddress().equals(person.getAddress())) {
                return true;
            }
        }
        return false;
    }

    public FireStationDto getPersonsByStation(int stationNumber) {

        List<String> coveredAddresses = new ArrayList<>();
        for (FireStation fs : fireStationRepository.findAllFireStationsByNumber(stationNumber)) {
            coveredAddresses.add(fs.getAddress());
        }

        List<Person> personsCovered = new ArrayList<>();
        for (Person p : personService.allPersons()) {
            if (coveredAddresses.contains(p.getAddress())) {
                personsCovered.add(p);
            }
        }

        int adultCount = 0;
        int childCount = 0;

        List<PersonDto> personDTOList = new ArrayList<>();

        for (Person p : personsCovered) {

            MedicalRecord record = null;
            for (MedicalRecord mr : personService.getMedicalRecords()) {
                if (mr.getFirstName().equalsIgnoreCase(p.getFirstName()) &&
                        mr.getLastName().equalsIgnoreCase(p.getLastName())) {
                    record = mr;
                    break;
                }
            }

            int age = (record != null) ? personService.computeAge(record.getBirthdate()) : 0;

            if (age > 18) adultCount++;
            else childCount++;

            PersonDto dto = new PersonDto();
            dto.setFirstName(p.getFirstName());
            dto.setLastName(p.getLastName());
            dto.setAddress(p.getAddress());
            dto.setPhone(p.getPhone());
            dto.setAge(age);

            personDTOList.add(dto);
        }

        return new FireStationDto(personDTOList, adultCount, childCount);
    }

    public FloodDto getFoyersByStations(int number) {

        List<String> coveredAddresses = new ArrayList<>();
        for (FireStation fs : fireStationRepository.findAllFireStationsByNumber(number)) {
            coveredAddresses.add(fs.getAddress());
        }

        List<Person> personsCovered = new ArrayList<>();
        for (Person p : personService.allPersons()) {
            if (coveredAddresses.contains(p.getAddress())) {
                personsCovered.add(p);
            }
        }

        List<FloodPersonDto> FloodPersonDTO = new ArrayList<>();


        for (Person p : personsCovered) {
            // Récupérer le record via ton repo
            MedicalRecord record = medicalRecordRepository.findMedicalWithFirstNameAndLastName(
                    p.getFirstName(),
                    p.getLastName()
            );

            int age = (record != null) ? personService.computeAge(record.getBirthdate()) : 0;

            FloodPersonDto dto = new FloodPersonDto();
            dto.setFirstName(p.getFirstName());
            dto.setLastName(p.getLastName());
            dto.setPhone(p.getPhone());
            dto.setAge(age);

            if (record != null) {
                dto.setAllergies(record.getAllergies().toArray(new String[0]));
                dto.setMedications(record.getMedications().toArray(new String[0]));
            }

            FloodPersonDTO.add(dto);

        }
        return new FloodDto(FloodPersonDTO);

    }
}