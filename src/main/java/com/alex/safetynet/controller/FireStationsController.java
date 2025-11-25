package com.alex.safetynet.controller;

import com.alex.safetynet.model.FireStation;
import com.alex.safetynet.service.FireStationService;
import com.alex.safetynet.service.dto.FireStationDto;
import com.alex.safetynet.service.dto.FloodDto;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
public class FireStationsController {

    private final FireStationService fireStationService;

    public FireStationsController(FireStationService fireStationService) {
        this.fireStationService = fireStationService;
    }

    // Get all firestations
    @GetMapping("firestations")
    public List<FireStation> allFireStations() {
        return this.fireStationService.allFireStations();
    }

    // Get the phones numbers by fireStations
    @RequestMapping(value = "phoneAlert", method = RequestMethod.GET)
    public List<String> phoneNumberList(@RequestParam(name = "fireStation") int number) {
        return this.fireStationService.findPhoneNumbersByStationNumber(number);
    }

    //firestation?stationNumber=<station_number>
    @RequestMapping(value = "firestation", method = RequestMethod.GET)
    public FireStationDto personsListByFireStation(@RequestParam(name = "stationNumber") int number){
        return this.fireStationService.getPersonsByStation(number);
    }

    //flood/stations?stations=<foyers desservis>
    @RequestMapping(value = "flood/stations", method = RequestMethod.GET)
    public FloodDto foyersListByFireStation(@RequestParam(name = "stations") int number){
        return this.fireStationService.getFoyersByStations(number);
    }


}
