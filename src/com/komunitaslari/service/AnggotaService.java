package com.komunitaslari.service;

import com.komunitaslari.model.Anggota;
import java.util.List;

public interface AnggotaService {
    // CRUD operations
    void addAnggota(Anggota anggota) throws Exception;
    void updateAnggota(Anggota anggota) throws Exception;
    void deleteAnggota(int id) throws Exception;
    
    // Read operations
    List<Anggota> getAllAnggota() throws Exception;
    List<Anggota> searchAnggota(String keyword) throws Exception;
    Anggota getAnggotaById(int id) throws Exception;
    
    // Validation methods (sesuai dengan yang digunakan di Service Implementation)
    boolean isEmailExists(String email) throws Exception;
    boolean isEmailExists(String email, int excludeId) throws Exception;
    
    // Login method
    Anggota login(String email, String password) throws Exception;
    
    // Optional: Statistics
    int countByStatus(Anggota.StatusAnggota status) throws Exception;
    int countAll() throws Exception;
}