package com.komunitaslari.dao.mysql;

import com.komunitaslari.config.ConnectionPool;
import com.komunitaslari.dao.PengumumanDAO;
import com.komunitaslari.model.Pengumuman;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PengumumanDAOImpl implements PengumumanDAO {
    @Override
    public List<Pengumuman> findAll() throws SQLException {
        // Menggunakan id DESC agar pengumuman terbaru tetap muncul di atas
        String sql = "SELECT * FROM pengumuman ORDER BY id DESC"; 
        List<Pengumuman> list = new ArrayList<>();
        try (Connection conn = ConnectionPool.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                list.add(mapResultSetToPengumuman(rs));
            }
        }
        return list;
    }

    private Pengumuman mapResultSetToPengumuman(ResultSet rs) throws SQLException {
        Pengumuman p = new Pengumuman();
        p.setId(rs.getInt("id"));
        p.setJudul(rs.getString("judul"));
        p.setIsi(rs.getString("konten"));
        // Jika tidak ada kolom tanggal di DB, kita set null atau waktu sekarang
        p.setTanggalKirim(null); 
        return p;
    }

    @Override
    public int countAll() throws SQLException {
        String sql = "SELECT COUNT(*) FROM pengumuman";
        try (Connection conn = ConnectionPool.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            return rs.next() ? rs.getInt(1) : 0;
        }
    }

    // Method lainnya kosongkan saja dulu agar tidak error compile
    @Override public int create(Pengumuman p) throws SQLException { return 0; }
    @Override public Pengumuman findById(int id) throws SQLException { return null; }
    @Override public boolean update(Pengumuman p) throws SQLException { return false; }
    @Override public boolean delete(int id) throws SQLException { return false; }
    @Override public List<Pengumuman> search(String k) throws SQLException { return findAll(); }
    @Override public List<Pengumuman> findLatest(int l) throws SQLException { return findAll(); }
    @Override public List<Pengumuman> findByPengirim(String p) throws SQLException { return findAll(); }
}