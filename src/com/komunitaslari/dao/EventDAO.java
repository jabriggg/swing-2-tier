package com.komunitaslari.dao;

import com.komunitaslari.model.Event;
import java.sql.SQLException;
import java.util.List;

public interface EventDAO {
    int create(Event event) throws SQLException;
    List<Event> findAll() throws SQLException;
    boolean update(Event event) throws SQLException;
    boolean delete(int id) throws SQLException;
    Event findById(int id) throws SQLException;
    List<Event> search(String keyword) throws SQLException;
    List<Event> findByStatus(String status) throws SQLException;
    // Tambahkan ini agar controller tidak error saat update status saja
    boolean updateStatus(int id, String status) throws SQLException;
int countAll() throws SQLException;
int countByStatus(String status) throws SQLException;
}