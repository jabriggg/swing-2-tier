package com.komunitaslari.dao.mysql;
import com.komunitaslari.config.ConnectionPool;
import com.komunitaslari.dao.EventDAO;
import com.komunitaslari.model.Event;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EventDAOImpl implements EventDAO {
    
    @Override
    public List<Event> findAll() throws SQLException {
        String sql = "SELECT * FROM event ORDER BY tanggal_event DESC";
        List<Event> list = new ArrayList<>();
        Connection conn = null;
        try {
            conn = ConnectionPool.getConnection();
            try (Statement stmt = conn.createStatement();
                 ResultSet rs = stmt.executeQuery(sql)) {
                while (rs.next()) { list.add(mapResultSetToEvent(rs)); }
            }
        } finally {
            ConnectionPool.releaseConnection(conn);
        }
        return list;
    }

    @Override
    public int countAll() throws SQLException {
        String sql = "SELECT COUNT(*) FROM event";
        Connection conn = null;
        try {
            conn = ConnectionPool.getConnection();
            try (Statement stmt = conn.createStatement();
                 ResultSet rs = stmt.executeQuery(sql)) {
                return rs.next() ? rs.getInt(1) : 0;
            }
        } finally {
            ConnectionPool.releaseConnection(conn);
        }
    }

   @Override
public int countByStatus(String status) throws SQLException {
    String sql = "SELECT COUNT(*) FROM event WHERE status = ?";
    Connection conn = null;
    try {
        conn = ConnectionPool.getConnection();
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, status);
            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next() ? rs.getInt(1) : 0;
            }
        }
    } finally {
        // Ini yang bikin error "Batas Maksimal" ilang!
        ConnectionPool.releaseConnection(conn);
    }
}

    private Event mapResultSetToEvent(ResultSet rs) throws SQLException {
        Event e = new Event();
        e.setId(rs.getInt("id"));
        e.setNamaEvent(rs.getString("nama_event"));
        Timestamp ts = rs.getTimestamp("tanggal_event");
        if (ts != null) e.setTanggalEvent(ts.toLocalDateTime());
        e.setLokasi(rs.getString("lokasi"));
        e.setStatus(rs.getString("status"));
        return e;
    }

    // Method lain (create, update, delete) sesuaikan pola finally releaseConnection yang sama
    @Override public int create(Event e) throws SQLException { return 0; }
    @Override public boolean update(Event e) throws SQLException { return false; }
    @Override public boolean delete(int id) throws SQLException { return false; }
    @Override public Event findById(int id) throws SQLException { return null; }
    @Override public List<Event> search(String k) throws SQLException { return findAll(); }
    @Override public List<Event> findByStatus(String s) throws SQLException { return findAll(); }
    @Override public boolean updateStatus(int id, String s) throws SQLException { return true; }
}