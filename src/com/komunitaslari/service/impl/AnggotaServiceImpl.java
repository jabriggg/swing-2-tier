package com.komunitaslari.service.impl;

import com.komunitaslari.dao.AnggotaDAO;
import com.komunitaslari.dao.mysql.AnggotaDAOImpl;
import com.komunitaslari.model.Anggota;
import com.komunitaslari.service.AnggotaService;

import java.util.List;

public class AnggotaServiceImpl implements AnggotaService {

    private final AnggotaDAO dao;

    public AnggotaServiceImpl() {
        this.dao = new AnggotaDAOImpl();
    }

    @Override
    public void addAnggota(Anggota anggota) throws Exception {
        if (dao.isEmailExists(anggota.getEmail())) {
            throw new Exception("Email " + anggota.getEmail() + " sudah terdaftar!");
        }
        dao.create(anggota);
    }

    @Override
    public void updateAnggota(Anggota anggota) throws Exception {
        if (dao.isEmailExists(anggota.getEmail(), anggota.getId())) {
            throw new Exception("Email sudah digunakan oleh anggota lain!");
        }
        boolean success = dao.update(anggota);
        if (!success) {
            throw new Exception("Gagal mengupdate data anggota.");
        }
    }

    @Override
    public void deleteAnggota(int id) throws Exception {
        boolean success = dao.delete(id);
        if (!success) {
            throw new Exception("Gagal menghapus anggota. Data mungkin sudah tidak ada.");
        }
    }

    @Override
    public List<Anggota> getAllAnggota() throws Exception {
        return dao.findAll();
    }

    @Override
    public List<Anggota> searchAnggota(String keyword) throws Exception {
        return dao.search(keyword);
    }

    @Override
    public Anggota getAnggotaById(int id) throws Exception {
        return dao.findById(id);
    }

    @Override
    public boolean isEmailExists(String email) throws Exception {
        return dao.isEmailExists(email);
    }

    @Override
    public boolean isEmailExists(String email, int excludeId) throws Exception {
        return dao.isEmailExists(email, excludeId);
    }

    @Override
    public Anggota login(String email, String password) throws Exception {
        return dao.login(email, password);
    }

    @Override
    public int countByStatus(Anggota.StatusAnggota status) throws Exception {
        return dao.countByStatus(status);
    }

    @Override
    public int countAll() throws Exception {
        return dao.countAll();
    }
}