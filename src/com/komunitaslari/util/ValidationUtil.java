package com.komunitaslari.util;

import java.util.regex.Pattern;

/**
 * Utility untuk validasi input di seluruh aplikasi
 */
public class ValidationUtil {

    // --- Logika Validasi ---

    public static boolean isEmpty(String value) {
        return value == null || value.trim().isEmpty();
    }

    public static boolean isNotEmpty(String value) {
        return !isEmpty(value);
    }

    public static boolean isValidEmail(String email) {
        if (isEmpty(email)) return false;
        String emailRegex = "^[A-Za-z0-9+_.-]+@(.+)$";
        return Pattern.compile(emailRegex).matcher(email).matches();
    }

    public static boolean isValidPhone(String phone) {
        if (isEmpty(phone)) return false;
        return phone.matches("\\d{10,15}");
    }

    public static boolean hasMinLength(String value, int min) {
        return value != null && value.trim().length() >= min;
    }

    public static boolean hasMaxLength(String value, int max) {
        return value != null && value.trim().length() <= max;
    }

    public static boolean isValidDate(String dateStr) {
        return DateUtil.parseDate(dateStr) != null;
    }

    // --- Method Message (Buat nampilin error di Dialog) ---

    public static String getRequiredMessage(String field) {
        return field + " wajib diisi!";
    }

    public static String getInvalidFormatMessage(String field) {
        return "Format " + field + " tidak valid!";
    }

    public static String getMinLengthMessage(String field, int min) {
        return field + " minimal harus " + min + " karakter!";
    }

    public static String getMaxLengthMessage(String field, int max) {
        return field + " maksimal " + max + " karakter!";
    }
}