package com.esliceu.core.utils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public final class DateParser {

    public static LocalDate dataParser(String date) throws Exception {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyy-M-d", Locale.ENGLISH);
        LocalDate date1 = LocalDate.parse(date, formatter);
        System.out.println(date);
        return date1;
    }

    private DateParser() {
    }
}
