package com.odontomed.util;

import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Component
public class FormatDate {

    public LocalDate stringToDate(String string){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/MM/yyyy");
        LocalDate date = LocalDate.parse(string, formatter);
        formatter.format(date);
        return date;
    }

    public LocalDate dateToDate(LocalDate date){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/MM/yyyy");
        formatter.format(date);
        return date;
    }
}