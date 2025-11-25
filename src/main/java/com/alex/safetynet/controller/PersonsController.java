package com.alex.safetynet.controller;

import com.alex.safetynet.service.PersonService;
import com.alex.safetynet.service.dto.ChildAlertDto;
import com.alex.safetynet.service.dto.PersonInfoDto;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class PersonsController {

    private final PersonService personService;

    public PersonsController(PersonService personService) {
        this.personService = personService;
    }

    // Get mail by city
    @GetMapping("/communityEmail")
    public List<String> findAllEmails(@RequestParam(name = "city")String city) {
        return personService.findAllEmails(city);
    }

    /*   @GetMapping("/phones")
    public List<String> firestationNumber(String phone) {
        return personService.firestationNumbers(phone);
    } */

    @RequestMapping(value = "personInfo", method = RequestMethod.GET)
    public List<PersonInfoDto> listOfPersonsWithMedicalRecords(@RequestParam String firstName, @RequestParam String lastName){
         return this.personService.findAllPersonsWithMedicalRecords(firstName, lastName);
    }

    @RequestMapping(value = "childAlert", method = RequestMethod.GET)
    public List<ChildAlertDto> childsUnder18ByAddress(@RequestParam(name = "address") String address) {
        return this.personService.findAllChildsUnder18ByAddress(address);
    }






}
