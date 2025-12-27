package com.komunitaslari.dao;

import com.komunitaslari.model.Anggota;
import java.util.List;

public interface AnggotaDAO {
    int create(Anggota anggota) throws Exception;
    boolean update(Anggota anggota) throws Exception;
    boolean delete(int id) throws Exception;
    List<Anggota> findAll() throws Exception;
    Anggota findById(int id) throws Exception;
    boolean isEmailExists(String email) throws Exception;
    boolean isEmailExists(String email, int excludeId) throws Exception;
    Anggota login(String email, String password) throws Exception;
    List<Anggota> search(String keyword) throws Exception;
    int countAll() throws Exception;
    int countByStatus(Anggota.StatusAnggota status) throws Exception;
}