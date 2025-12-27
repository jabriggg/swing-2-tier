package com.komunitaslari.dao;

import com.komunitaslari.dao.mysql.PengumumanDAOImpl;
// Import DAO lain kalau ada, misal AnggotaDAOImpl

/**
 * Factory untuk menyediakan instance DAO
 */
public class DAOFactory {
    
    private static PengumumanDAO pengumumanDAO;

    public static PengumumanDAO getPengumumanDAO() {
        if (pengumumanDAO == null) {
            pengumumanDAO = new PengumumanDAOImpl();
        }
        return pengumumanDAO;
    }
    
    // Nanti tambahin buat AnggotaDAO, EventDAO, dsb di sini
}