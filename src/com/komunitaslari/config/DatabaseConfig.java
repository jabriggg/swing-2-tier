package com.komunitaslari.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Konfigurasi koneksi database MySQL
 */
public class DatabaseConfig {
    
    // Konfigurasi database
    private static final String DB_URL = "jdbc:mysql://localhost:3306/db_komunitas_lari";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "password"; // Sesuaikan dengan password MySQL Anda
    private static final String DB_DRIVER = "com.mysql.cj.jdbc.Driver";
    
    // Connection pool parameters
    private static final int MAX_POOL_SIZE = 40;
    private static final int MIN_POOL_SIZE = 2;
    
    static {
        try {
            // Load MySQL JDBC Driver
            Class.forName(DB_DRIVER);
            System.out.println("MySQL JDBC Driver berhasil dimuat");
        } catch (ClassNotFoundException e) {
            System.err.println("Error: MySQL JDBC Driver tidak ditemukan!");
            e.printStackTrace();
        }
    }
    
    /**
     * Mendapatkan koneksi database baru
     * @return Connection object
     * @throws SQLException jika koneksi gagal
     */
    public static Connection getConnection() throws SQLException {
        try {
            Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            System.out.println("Koneksi database berhasil dibuat");
            return conn;
        } catch (SQLException e) {
            System.err.println("Error saat membuat koneksi database!");
            throw e;
        }
    }
    
    /**
     * Test koneksi database
     * @return true jika koneksi berhasil, false jika gagal
     */
    public static boolean testConnection() {
        try (Connection conn = getConnection()) {
            return conn != null && !conn.isClosed();
        } catch (SQLException e) {
            System.err.println("Test koneksi gagal: " + e.getMessage());
            return false;
        }
    }
    
    /**
     * Menutup koneksi database dengan aman
     * @param conn Connection yang akan ditutup
     */
    public static void closeConnection(Connection conn) {
        if (conn != null) {
            try {
                conn.close();
                System.out.println("Koneksi database ditutup");
            } catch (SQLException e) {
                System.err.println("Error saat menutup koneksi: " + e.getMessage());
            }
        }
    }
    
    // Getter methods
    public static String getDbUrl() {
        return DB_URL;
    }
    
    public static String getDbUser() {
        return DB_USER;
    }
    
    public static int getMaxPoolSize() {
        return MAX_POOL_SIZE;
    }
    
    public static int getMinPoolSize() {
        return MIN_POOL_SIZE;
    }
}