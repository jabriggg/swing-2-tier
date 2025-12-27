package com.komunitaslari.service.impl;

import com.komunitaslari.dao.PengumumanDAO;
import com.komunitaslari.dao.mysql.PengumumanDAOImpl;
import com.komunitaslari.model.Pengumuman;
import com.komunitaslari.service.PengumumanService;

import java.sql.SQLException;
import java.util.List;

public class PengumumanServiceImpl implements PengumumanService {

    private final PengumumanDAO pengumumanDAO;

    public PengumumanServiceImpl() {
        // Memanggil implementasi MySQL DAO
        this.pengumumanDAO = new PengumumanDAOImpl();
    }

    @Override
    public List<Pengumuman> getAllPengumuman() throws Exception {
        try {
            return pengumumanDAO.findAll();
        } catch (SQLException e) {
            throw new Exception("Gagal mengambil semua data pengumuman: " + e.getMessage());
        }
    }

    @Override
    public List<Pengumuman> getRecentPengumuman(int limit) throws Exception {
        try {
            return pengumumanDAO.findLatest(limit);
        } catch (SQLException e) {
            throw new Exception("Gagal mengambil pengumuman terbaru: " + e.getMessage());
        }
    }

    @Override
    public List<Pengumuman> searchPengumuman(String keyword) throws Exception {
        try {
            return pengumumanDAO.search(keyword);
        } catch (SQLException e) {
            throw new Exception("Gagal mencari pengumuman: " + e.getMessage());
        }
    }

    @Override
    public Pengumuman getPengumumanById(int id) throws Exception {
        try {
            return pengumumanDAO.findById(id);
        } catch (SQLException e) {
            throw new Exception("Gagal mengambil data pengumuman ID " + id + ": " + e.getMessage());
        }
    }

    @Override
    public boolean savePengumuman(Pengumuman pengumuman) throws Exception {
        try {
            // DAO.create mengembalikan int (ID), kita ubah jadi boolean untuk Service
            int id = pengumumanDAO.create(pengumuman);
            return id > 0;
        } catch (SQLException e) {
            throw new Exception("Gagal menyimpan pengumuman: " + e.getMessage());
        }
    }

    @Override
    public boolean updatePengumuman(Pengumuman pengumuman) throws Exception {
        try {
            return pengumumanDAO.update(pengumuman);
        } catch (SQLException e) {
            throw new Exception("Gagal memperbarui pengumuman: " + e.getMessage());
        }
    }

    @Override
    public boolean deletePengumuman(int id) throws Exception {
        try {
            return pengumumanDAO.delete(id);
        } catch (SQLException e) {
            throw new Exception("Gagal menghapus pengumuman: " + e.getMessage());
        }
    }

    @Override
    public int getTotalPengumuman() throws Exception {
        try {
            return pengumumanDAO.countAll();
        } catch (SQLException e) {
            throw new Exception("Gagal menghitung total pengumuman: " + e.getMessage());
        }
    }
}