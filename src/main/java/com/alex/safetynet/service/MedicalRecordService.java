package com.alex.safetynet.service;

import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;

@Service
public class MedicalRecordService {

    public class AgeUtils {
        public static int computeAge(String birthdate) {
            if (birthdate == null || birthdate.isBlank()) return 0;
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
            LocalDate date = LocalDate.parse(birthdate, formatter);
            return Period.between(date, LocalDate.now()).getYears();
        }
    }





}
