package com.alex.safetynet.repository;

import com.alex.safetynet.model.MedicalRecord;
import org.springframework.stereotype.Component;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class MedicalRecordRepository {

    private final DataHandler dataHandler;

    public MedicalRecordRepository(DataHandler dataHandler) {
        this.dataHandler = dataHandler;
    }

    public MedicalRecord findMedicalWithFirstNameAndLastName(String firstName, String lastName) {
        return dataHandler.getData().getMedicalRecords().stream()
                .filter(p -> p.getFirstName().equals(firstName))
                .filter(p -> p.getLastName().equals(lastName))
                .findFirst()
                .orElseGet(MedicalRecord::new);
    }

    public List<MedicalRecord> findAllMedicalRecords() {
        return dataHandler.getData().getMedicalRecords();
    }

    public List<MedicalRecord> findAllMedicalRecordsUnder18() {
        return dataHandler.getData().getMedicalRecords().stream()
                .filter(m -> isUnder18(m.getBirthdate()))
                .collect(Collectors.toList());
    }

    private boolean isUnder18(String birthdate) {
        Date date = null;
        try {
            date = new SimpleDateFormat("dd/MM/yyyy").parse(birthdate);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Calendar calendar = GregorianCalendar.getInstance();
        calendar.set(Calendar.YEAR, calendar.get(Calendar.YEAR) - 18);

        return date != null && !calendar.getTime().after(date);
    }
}
