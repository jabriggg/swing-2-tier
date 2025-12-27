package com.komunitaslari.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionPool {
    // Pakai koneksi standar dulu supaya nggak ribet leak-nya
    public static Connection getConnection() throws SQLException {
        // Sesuaikan nama database, user, dan password lu
        String url = "jdbc:mysql://localhost:3306/db_komunitas_lari";
        String user = "root";
        String pass = "password"; 
        return DriverManager.getConnection(url, user, pass);
    }

    // Biar nggak error saat dipanggil di DAO
    public static void releaseConnection(Connection conn) {
        if (conn != null) {
            try {
                conn.close(); // Tutup beneran supaya nggak limit
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}