package com.alex.safetynet.repository;

import com.alex.safetynet.model.Data;
import com.alex.safetynet.model.FireStation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class FireStationRepository {
    @Autowired
    private Data data;

    private final DataHandler dataHandler;

    public FireStationRepository(DataHandler datahandler) {this.dataHandler = datahandler;}

    public List<FireStation> findAllFireStations() {return dataHandler.getData().getFirestations();}

    public List<FireStation> findAllFireStationsByNumber(Integer number) {
    return dataHandler.getData().getFirestations().stream()
            .filter(f  -> f.getStation()
            .equals(number.toString())).collect(Collectors.toList());
    }

}
