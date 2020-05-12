package com.esliceu.core.utils;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
public final class DateParser {

    public static LocalDate dataParser(String date) throws Exception {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-M-d", Locale.ENGLISH);
        LocalDate date1 = LocalDate.parse(date, formatter);
        System.out.println(date);
        return date1;
    }

    public static LocalTime horaParser(String hora){
        return LocalTime.parse(hora);
    }



    private DateParser() {
    }
}
