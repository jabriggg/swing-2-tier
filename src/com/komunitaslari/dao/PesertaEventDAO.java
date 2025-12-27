package com.komunitaslari.dao;

import com.komunitaslari.model.PesertaEvent;
import java.sql.SQLException;
import java.util.List;

public interface PesertaEventDAO {
    // Method yang sudah ada di kode kamu
    int create(PesertaEvent peserta) throws SQLException;
    List<PesertaEvent> findByEventId(int eventId) throws SQLException;
    boolean delete(int id) throws SQLException;
    boolean isAlreadyRegistered(int anggotaId, int eventId) throws SQLException;

    // TAMBAHKAN method ini agar error di Implementasi hilang:
    void save(PesertaEvent peserta) throws SQLException;
    List<PesertaEvent> findAll() throws SQLException;
    List<PesertaEvent> findByAnggotaId(int anggotaId) throws SQLException;
    int countByEventId(int eventId) throws SQLException;
    boolean deleteByAnggotaAndEvent(int anggotaId, int eventId) throws SQLException;
}