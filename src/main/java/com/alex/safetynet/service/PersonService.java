package com.alex.safetynet.service;

import com.alex.safetynet.model.MedicalRecord;
import com.alex.safetynet.model.Person;
import com.alex.safetynet.repository.PersonRepository;
import com.alex.safetynet.repository.FireStationRepository;
import com.alex.safetynet.repository.MedicalRecordRepository;
import com.alex.safetynet.service.dto.ChildAlertDto;
import com.alex.safetynet.service.dto.PersonInfoDto;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
public class PersonService {

    private final PersonRepository personRepository;
    private final FireStationRepository fireStationRepository;
    private final MedicalRecordRepository medicalRecordRepository;

    public PersonService(PersonRepository personRepository,
                         FireStationRepository fireStationRepository,
                         MedicalRecordRepository medicalRecordRepository) {
        this.personRepository = personRepository;
        this.fireStationRepository = fireStationRepository;
        this.medicalRecordRepository = medicalRecordRepository;
    }



    // 🔹 Récupère toutes les personnes
    public List<Person> allPersons() {
        return personRepository.findAllPersons();
    }

    // 🔹 Emails par ville
    public List<String> findAllEmails(String city) {
        List<String> emails = new ArrayList<>();
        List<Person> persons = personRepository.findAllPersons();

        for (Person person : persons) {
            if (person.getCity().equals(city)) {
                emails.add(person.getEmail());
            }
        }
        return emails;
    }

    // 🔹 Infos détaillées d’une personne avec dossier médical
    public List<PersonInfoDto> findAllPersonsWithMedicalRecords(String firstName, String lastName) {
        List<PersonInfoDto> result = new ArrayList<>();
        Person person = personRepository.findpersonByfirstNameAndLastName(firstName, lastName);
        MedicalRecord medicalRecord = medicalRecordRepository.findMedicalWithFirstNameAndLastName(firstName, lastName);

        if (person != null && medicalRecord != null) {
            PersonInfoDto dto = new PersonInfoDto();
            dto.setFirstName(person.getFirstName());
            dto.setLastName(person.getLastName());
            dto.setAddress(person.getAddress());
            dto.setAge(Integer.parseInt(String.valueOf(computeAge(medicalRecord.getBirthdate()))));
            dto.setEmail(person.getEmail());
            dto.setAllergies(medicalRecord.getAllergies().toArray(new String[0]));
            dto.setMedications(medicalRecord.getMedications().toArray(new String[0]));
            result.add(dto);
        }

        return result;
    }

    // 🔹 Alias de allPersons (inutile si doublon)
    public List<Person> findAllPersons() {
        return personRepository.findAllPersons();
    }

    // 🔹 Enfants (<18 ans) par adresse avec membres du foyer
    public List<ChildAlertDto> findAllChildsUnder18ByAddress(String address) {
        List<ChildAlertDto> result = new ArrayList<>();
        List<Person> personsAtAddress = personRepository.findAllPersonByAddress(address);

        for (Person person : personsAtAddress) {
            MedicalRecord record = medicalRecordRepository.findMedicalWithFirstNameAndLastName(
                    person.getFirstName(), person.getLastName()
            );

            if (record != null) {
                int age = computeAge(record.getBirthdate());
                if (age < 18) {
                    ChildAlertDto dto = new ChildAlertDto();
                    dto.setFirstName(person.getFirstName());
                    dto.setLastName(person.getLastName());
                    dto.setAge(String.valueOf(age));

                    // Autres membres du foyer
                    List<Person> household = personsAtAddress.stream()
                            .filter(p -> !(p.getFirstName().equals(person.getFirstName())
                                    && p.getLastName().equals(person.getLastName())))
                            .toList();

                    dto.setHouseholds(household);
                    result.add(dto);
                }
            }
        }
        return result;
    }

    // 🔹 Calcul de l’âge
    public int computeAge(String birthdate) {
        if (birthdate == null || birthdate.isBlank()) return 0;

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        LocalDate date = LocalDate.parse(birthdate, formatter);

        return Period.between(date, LocalDate.now()).getYears();
    }

   public List<MedicalRecord> getMedicalRecords() {
    return medicalRecordRepository.findAllMedicalRecords();}
}