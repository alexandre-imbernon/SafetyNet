package com.alex.safetynet.service;

import com.alex.safetynet.model.FireStation;
import com.alex.safetynet.model.MedicalRecord;
import com.alex.safetynet.model.Person;
import com.alex.safetynet.repository.FireStationRepository;
import com.alex.safetynet.repository.MedicalRecordRepository;
import com.alex.safetynet.repository.PersonRepository;
import com.alex.safetynet.service.dto.*;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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

    /**
     * Récupère les numéros de téléphone des personnes couvertes par une station
     */
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

            MedicalRecord record = personService.getMedicalRecords().stream()
                    .filter(mr -> mr.getFirstName().equalsIgnoreCase(p.getFirstName())
                            && mr.getLastName().equalsIgnoreCase(p.getLastName()))
                    .findFirst()
                    .orElse(null);

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

        List<FloodPersonDto> floodPersonDTO = new ArrayList<>();

        for (Person p : personsCovered) {

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

            floodPersonDTO.add(dto);
        }

        return new FloodDto(coveredAddresses.get(0), floodPersonDTO);
    }


    public List<FloodDto2> flood(List<Integer> stationsNumbers) {
        return stationsNumbers.stream()
                .flatMap(n -> fireStationRepository.findAllFireStationsByNumber(n).stream())
                .map(s -> FloodDto2.builder()
                        .address(s.getAddress())
                        .people(getPeopleByAddress(s.getAddress()))
                        .build())
                .collect(Collectors.toList());
    }


    public List<FloodDto2.PersonDto2> getPeopleByAddress(String address) {
        return personRepository.findAllPersonByAddress(address).stream()
                .map(p -> mapToPerson(p))
                .collect(Collectors.toList());
    }


    public FloodDto2.PersonDto2 mapToPerson(Person person) {
        MedicalRecord record = medicalRecordRepository
                .findMedicalWithFirstNameAndLastName(person.getFirstName(), person.getLastName());

        return FloodDto2.PersonDto2.builder()
                .lastName(person.getLastName())
                .phoneNumber(person.getPhone())
                .age(MedicalRecordService.AgeUtils.computeAge(record.getBirthdate()))   // utilisation directe
                .medications(record.getMedications().toArray(new String[0]))
                .allergies(record.getAllergies().toArray(new String[0]))
                .build();
    }










}