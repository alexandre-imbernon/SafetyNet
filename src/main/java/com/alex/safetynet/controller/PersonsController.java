package com.alex.safetynet.controller;

import com.alex.safetynet.model.Person;
import com.alex.safetynet.repository.PersonRepository;
import com.alex.safetynet.service.PersonService;
import com.alex.safetynet.service.dto.ChildAlertDto;
import com.alex.safetynet.service.dto.PersonInfoDto;
import org.springframework.web.bind.annotation.*;

import java.util.List;

    @RestController
    public class PersonsController {

        private final PersonService personService;
        private final PersonRepository personRepository;
        public PersonsController(PersonService personService, PersonRepository personRepository) {
            this.personService = personService;
            this.personRepository = personRepository;
        }

    // Get mail by city
    @GetMapping("/communityEmail")
    public List<String> findAllEmails(@RequestParam(name = "city") String city) {
        return personService.findAllEmails(city);
    }

    /*   @GetMapping("/phones")
    public List<String> firestationNumber(String phone) {
        return personService.firestationNumbers(phone);
    } */
    @RequestMapping(value = "personInfo", method = RequestMethod.GET)
    public List<PersonInfoDto> listOfPersonsWithMedicalRecords(@RequestParam String firstName, @RequestParam String lastName) {
        return this.personService.findAllPersonsWithMedicalRecords(firstName, lastName);
    }

    @RequestMapping(value = "childAlert", method = RequestMethod.GET)
    public List<ChildAlertDto> childsUnder18ByAddress(@RequestParam(name = "address") String address) {
        return this.personService.findAllChildsUnder18ByAddress(address);
    }

    @GetMapping("/getPersons")
    public List<Person> findAllPersons() {
        return personService.findAllPersons();
    }

    @PostMapping("/addPerson")
    public Person addPerson(@RequestBody Person person) {
        return personRepository.savePerson(person);
    }

    @PutMapping("/updatePerson")
    public Person updatePerson(@RequestBody Person person, @RequestParam String firstName, @RequestParam String lastName) {
        return personRepository.updatePerson(person, firstName, lastName);
    }

    @DeleteMapping("/deletePerson")
    public void deletePerson(@RequestParam String firstName, @RequestParam String lastName) {
        personRepository.deletePerson(firstName, lastName);
    }
}