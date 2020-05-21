package com.esliceu.core.utils;

import java.time.LocalTime;
public final class DateParser {

    public static LocalTime horaParser(String hora){
        return LocalTime.parse(hora);
    }
}
