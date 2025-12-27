package com.komunitaslari.util;

import java.time.LocalDate;
import java.time.LocalDateTime; // Tambahin import ini
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class DateUtil {
    private static final String DATE_PATTERN = "dd/MM/yyyy";
    private static final String DATE_TIME_PATTERN = "dd/MM/yyyy HH:mm"; // Buat pattern baru
    
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern(DATE_PATTERN);
    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern(DATE_TIME_PATTERN);

    public static String format(LocalDate date) {
        return (date == null) ? "" : FORMATTER.format(date);
    }

    // TAMBAHKAN METHOD INI: Biar bisa format LocalDateTime di PengumumanController
    public static String format(LocalDateTime dateTime) {
        return (dateTime == null) ? "" : TIME_FORMATTER.format(dateTime);
    }

    public static LocalDate parseDate(String dateString) {
        try {
            return LocalDate.parse(dateString, FORMATTER);
        } catch (DateTimeParseException e) {
            return null;
        }
    }
}