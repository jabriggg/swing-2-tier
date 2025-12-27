package com.komunitaslari.util;

import javax.swing.*;
import java.awt.*;

/**
 * Utility class untuk menampilkan dialog messages
 */
public class MessageUtil {
    
    /**
     * Tampilkan pesan sukses
     */
    public static void showSuccess(Component parent, String message) {
        JOptionPane.showMessageDialog(
            parent,
            message,
            "Sukses",
            JOptionPane.INFORMATION_MESSAGE
        );
    }
    
    /**
     * Tampilkan pesan error
     */
    public static void showError(Component parent, String message) {
        JOptionPane.showMessageDialog(
            parent,
            message,
            "Error",
            JOptionPane.ERROR_MESSAGE
        );
    }
    
    /**
     * Tampilkan pesan warning
     */
    public static void showWarning(Component parent, String message) {
        JOptionPane.showMessageDialog(
            parent,
            message,
            "Peringatan",
            JOptionPane.WARNING_MESSAGE
        );
    }
    
    /**
     * Tampilkan pesan info
     */
    public static void showInfo(Component parent, String message) {
        JOptionPane.showMessageDialog(
            parent,
            message,
            "Informasi",
            JOptionPane.INFORMATION_MESSAGE
        );
    }
    
    /**
     * Konfirmasi delete dengan custom message
     */
    public static boolean showDeleteConfirm(Component parent, String itemName) {
        int result = JOptionPane.showConfirmDialog(
            parent,
            "Apakah Anda yakin ingin menghapus " + itemName + "?\n" +
            "Data yang sudah dihapus tidak dapat dikembalikan!",
            "Konfirmasi Hapus",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.WARNING_MESSAGE
        );
        return result == JOptionPane.YES_OPTION;
    }
    
    /**
     * Konfirmasi umum (Yes/No)
     */
    public static boolean showConfirm(Component parent, String message) {
        int result = JOptionPane.showConfirmDialog(
            parent,
            message,
            "Konfirmasi",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.QUESTION_MESSAGE
        );
        return result == JOptionPane.YES_OPTION;
    }
    
    /**
     * Konfirmasi dengan Yes/No/Cancel
     */
    public static int showConfirmYesNoCancel(Component parent, String message) {
        return JOptionPane.showConfirmDialog(
            parent,
            message,
            "Konfirmasi",
            JOptionPane.YES_NO_CANCEL_OPTION,
            JOptionPane.QUESTION_MESSAGE
        );
    }
    
    /**
     * Input dialog sederhana
     */
    public static String showInputDialog(Component parent, String message) {
        return JOptionPane.showInputDialog(
            parent,
            message,
            "Input",
            JOptionPane.QUESTION_MESSAGE
        );
    }
    
    /**
     * Input dialog dengan default value
     */
    public static String showInputDialog(Component parent, String message, String defaultValue) {
        return (String) JOptionPane.showInputDialog(
            parent,
            message,
            "Input",
            JOptionPane.QUESTION_MESSAGE,
            null,
            null,
            defaultValue
        );
    }
    
    /**
     * Validasi field kosong
     */
    public static boolean validateNotEmpty(Component parent, String fieldName, String value) {
        if (value == null || value.trim().isEmpty()) {
            showWarning(parent, fieldName + " tidak boleh kosong!");
            return false;
        }
        return true;
    }
    
    /**
     * Validasi email format
     */
    public static boolean validateEmail(Component parent, String email) {
        if (email == null || email.trim().isEmpty()) {
            showWarning(parent, "Email tidak boleh kosong!");
            return false;
        }
        
        String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";
        if (!email.matches(emailRegex)) {
            showWarning(parent, "Format email tidak valid!");
            return false;
        }
        
        return true;
    }
    
    /**
     * Validasi password minimal length
     */
    public static boolean validatePassword(Component parent, String password, int minLength) {
        if (password == null || password.trim().isEmpty()) {
            showWarning(parent, "Password tidak boleh kosong!");
            return false;
        }
        
        if (password.length() < minLength) {
            showWarning(parent, "Password minimal " + minLength + " karakter!");
            return false;
        }
        
        return true;
    }
    
    /**
     * Validasi angka positif
     */
    public static boolean validatePositiveNumber(Component parent, String fieldName, String value) {
        try {
            int number = Integer.parseInt(value);
            if (number <= 0) {
                showWarning(parent, fieldName + " harus lebih dari 0!");
                return false;
            }
            return true;
        } catch (NumberFormatException e) {
            showWarning(parent, fieldName + " harus berupa angka!");
            return false;
        }
    }
    
    /**
     * Show custom error dengan detail exception
     */
    public static void showErrorWithDetail(Component parent, String message, Exception e) {
        String detailMessage = message + "\n\nDetail: " + e.getMessage();
        JTextArea textArea = new JTextArea(detailMessage);
        textArea.setEditable(false);
        textArea.setWrapStyleWord(true);
        textArea.setLineWrap(true);
        textArea.setCaretPosition(0);
        
        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setPreferredSize(new Dimension(400, 200));
        
        JOptionPane.showMessageDialog(
            parent,
            scrollPane,
            "Error",
            JOptionPane.ERROR_MESSAGE
        );
    }
}