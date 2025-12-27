package com.komunitaslari.dao.mysql;

import com.komunitaslari.config.ConnectionPool;
import com.komunitaslari.dao.PesertaEventDAO;
import com.komunitaslari.model.PesertaEvent;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PesertaEventDAOImpl implements PesertaEventDAO {

    @Override
    public int create(PesertaEvent peserta) throws SQLException {
        String sql = "INSERT INTO peserta_event (id_anggota, id_event, tanggal_daftar) VALUES (?, ?, ?)";
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conn = ConnectionPool.getConnection();
            stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            stmt.setInt(1, peserta.getIdAnggota());
            stmt.setInt(2, peserta.getIdEvent());
            stmt.setTimestamp(3, Timestamp.valueOf(peserta.getTanggalDaftar()));

            stmt.executeUpdate();
            rs = stmt.getGeneratedKeys();
            if (rs.next()) return rs.getInt(1);
            throw new SQLException("Gagal mendaftarkan peserta ke event.");
        } finally {
            closeResources(rs, stmt, conn);
        }
    }

    @Override
    public List<PesertaEvent> findByEventId(int eventId) throws SQLException {
        // Query sakti pake JOIN buat narik info detail Anggota
        String sql = "SELECT pe.*, a.nama as nama_anggota, a.email as email_anggota " +
                     "FROM peserta_event pe " +
                     "JOIN anggota a ON pe.id_anggota = a.id " +
                     "WHERE pe.id_event = ? ORDER BY pe.tanggal_daftar DESC";
        
        List<PesertaEvent> list = new ArrayList<>();
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conn = ConnectionPool.getConnection();
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, eventId);
            rs = stmt.executeQuery();
            while (rs.next()) {
                PesertaEvent pe = mapResultSetToPesertaEvent(rs);
                // Set data tambahan dari hasil JOIN
                pe.setNamaAnggota(rs.getString("nama_anggota"));
                pe.setEmailAnggota(rs.getString("email_anggota"));
                list.add(pe);
            }
        } finally {
            closeResources(rs, stmt, conn);
        }
        return list;
    }

    @Override
    public boolean delete(int id) throws SQLException {
        String sql = "DELETE FROM peserta_event WHERE id = ?";
        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn = ConnectionPool.getConnection();
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;
        } finally {
            closeResources(null, stmt, conn);
        }
    }

    @Override
    public boolean isAlreadyRegistered(int anggotaId, int eventId) throws SQLException {
        String sql = "SELECT COUNT(*) FROM peserta_event WHERE id_anggota = ? AND id_event = ?";
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conn = ConnectionPool.getConnection();
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, anggotaId);
            stmt.setInt(2, eventId);
            rs = stmt.executeQuery();
            if (rs.next()) return rs.getInt(1) > 0;
            return false;
        } finally {
            closeResources(rs, stmt, conn);
        }
    }

    // Tambahkan di dalam class PesertaEventDAOImpl

@Override
public void save(PesertaEvent peserta) throws SQLException {
    // Logika save (bisa panggil create jika id == 0)
}

@Override
public List<PesertaEvent> findAll() throws SQLException {
    return new ArrayList<>(); // Implementasikan query SELECT * nanti
}

@Override
public List<PesertaEvent> findByAnggotaId(int anggotaId) throws SQLException {
    return new ArrayList<>(); // Implementasikan query filter by id_anggota nanti
}

@Override
public int countByEventId(int eventId) throws SQLException {
    String sql = "SELECT COUNT(*) FROM peserta_event WHERE id_event = ?";
    // Implementasikan query COUNT nanti
    return 0; 
}

@Override
public boolean deleteByAnggotaAndEvent(int anggotaId, int eventId) throws SQLException {
    String sql = "DELETE FROM peserta_event WHERE id_anggota = ? AND id_event = ?";
    // Implementasikan executeUpdate nanti
    return false;
}

    private PesertaEvent mapResultSetToPesertaEvent(ResultSet rs) throws SQLException {
        PesertaEvent pe = new PesertaEvent();
        pe.setId(rs.getInt("id"));
        pe.setIdAnggota(rs.getInt("id_anggota"));
        pe.setIdEvent(rs.getInt("id_event"));
        pe.setTanggalDaftar(rs.getTimestamp("tanggal_daftar").toLocalDateTime());
        
        Timestamp createdAt = rs.getTimestamp("created_at");
        if (createdAt != null) pe.setCreatedAt(createdAt.toLocalDateTime());
        
        return pe;
    }

    private void closeResources(ResultSet rs, Statement stmt, Connection conn) {
        if (rs != null) try { rs.close(); } catch (SQLException e) {}
        if (stmt != null) try { stmt.close(); } catch (SQLException e) {}
        if (conn != null) ConnectionPool.releaseConnection(conn);
    }
}