package com.komunitaslari.dao.mysql;
import com.komunitaslari.dao.AnggotaDAO;
import com.komunitaslari.model.Anggota;
import com.komunitaslari.config.ConnectionPool;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AnggotaDAOImpl implements AnggotaDAO {
    @Override
    public int countAll() throws Exception {
        String sql = "SELECT COUNT(*) FROM anggota";
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
    public int countByStatus(Anggota.StatusAnggota status) throws Exception {
        String sql = "SELECT COUNT(*) FROM anggota WHERE status = ?";
        Connection conn = null;
        try {
            conn = ConnectionPool.getConnection();
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setString(1, status.name());
                try (ResultSet rs = stmt.executeQuery()) { return rs.next() ? rs.getInt(1) : 0; }
            }
        } finally {
            ConnectionPool.releaseConnection(conn);
        }
    }

    @Override
    public List<Anggota> findAll() throws Exception {
        String sql = "SELECT * FROM anggota ORDER BY nama ASC";
        List<Anggota> list = new ArrayList<>();
        Connection conn = null;
        try {
            conn = ConnectionPool.getConnection();
            try (Statement stmt = conn.createStatement();
                 ResultSet rs = stmt.executeQuery(sql)) {
                while (rs.next()) { list.add(mapResultSet(rs)); }
            }
        } finally {
            ConnectionPool.releaseConnection(conn);
        }
        return list;
    }

    private Anggota mapResultSet(ResultSet rs) throws SQLException {
        Anggota a = new Anggota();
        a.setId(rs.getInt("id"));
        a.setNama(rs.getString("nama"));
        a.setEmail(rs.getString("email"));
        try {
            a.setStatus(Anggota.StatusAnggota.valueOf(rs.getString("status").toUpperCase()));
        } catch (Exception e) { a.setStatus(Anggota.StatusAnggota.AKTIF); }
        return a;
    }

    // Method CRUD lainnya tambahkan pola finally { ConnectionPool.releaseConnection(conn); }
    @Override public int create(Anggota a) throws Exception { return 0; }
    @Override public boolean update(Anggota a) throws Exception { return false; }
    @Override public boolean delete(int id) throws Exception { return false; }
    @Override public Anggota findById(int id) throws Exception { return null; }
    @Override public Anggota login(String e, String p) throws Exception { return null; }
    @Override public boolean isEmailExists(String e) throws Exception { return false; }
    @Override public boolean isEmailExists(String e, int id) throws Exception { return false; }
    @Override public List<Anggota> search(String k) throws Exception { return findAll(); }
}