package com.odontomed.util;

import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Component
public class FormatDate {

    public LocalDate replaceWithFormat(String fecha){
        if(fecha.contains("/"))
            return stringToDate(fecha);

        fecha.replaceAll("-","/");
        return stringToDate(fecha);
    }

    public LocalDate stringToDate(String string){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate date = LocalDate.parse(string, formatter);
        formatter.format(date);
        return date;
    }

    public LocalDate dateToDate(LocalDate date){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        formatter.format(date);
        return date;
    }
}
