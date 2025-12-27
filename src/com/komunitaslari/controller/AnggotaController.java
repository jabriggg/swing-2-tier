package com.komunitaslari.controller;

import com.komunitaslari.model.Anggota;
import com.komunitaslari.service.AnggotaService;
import com.komunitaslari.service.impl.AnggotaServiceImpl;
import com.komunitaslari.util.MessageUtil;
import com.komunitaslari.view.AnggotaPanel;
import com.komunitaslari.worker.anggota.*;

import javax.swing.*;
import java.util.List;

public class AnggotaController {
    
    private final AnggotaPanel view;
    private final AnggotaService anggotaService;
    
    public AnggotaController(AnggotaPanel view) {
        this.view = view;
        this.anggotaService = new AnggotaServiceImpl();
    }
    
    /**
     * Load semua data anggota
     */
    public void loadData() {
        view.showLoading(true);
        
        // Bisa pakai worker custom atau SwingWorker biasa
        SwingWorker<List<Anggota>, Void> worker = new SwingWorker<>() {
            @Override
            protected List<Anggota> doInBackground() throws Exception {
                return anggotaService.getAllAnggota();
            }
            
            @Override
            protected void done() {
                view.showLoading(false);
                try {
                    List<Anggota> anggotaList = get();
                    view.updateTable(anggotaList);
                } catch (Exception e) {
                    MessageUtil.showError(view, "Gagal memuat data: " + e.getMessage());
                }
            }
        };
        worker.execute();
    }
    
    /**
     * Search anggota berdasarkan keyword
     */
    public void searchData(String keyword) {
        view.showLoading(true);
        
        SwingWorker<List<Anggota>, Void> worker = new SwingWorker<>() {
            @Override
            protected List<Anggota> doInBackground() throws Exception {
                return anggotaService.searchAnggota(keyword);
            }
            
            @Override
            protected void done() {
                view.showLoading(false);
                try {
                    List<Anggota> anggotaList = get();
                    view.updateTable(anggotaList);
                } catch (Exception e) {
                    MessageUtil.showError(view, "Gagal mencari data: " + e.getMessage());
                }
            }
        };
        worker.execute();
    }
    
    /**
     * Simpan anggota baru
     */
    public void saveAnggota(Anggota anggota) {
        view.showLoading(true);
        
        SwingWorker<Void, Void> worker = new SwingWorker<>() {
            @Override
            protected Void doInBackground() throws Exception {
                anggotaService.addAnggota(anggota);
                return null;
            }
            
            @Override
            protected void done() {
                view.showLoading(false);
                try {
                    get(); // Check for exception
                    MessageUtil.showSuccess(view, "Data anggota berhasil disimpan!");
                    loadData(); // Refresh table
                } catch (Exception e) {
                    MessageUtil.showError(view, e.getMessage());
                }
            }
        };
        worker.execute();
    }
    
    /**
     * Update data anggota
     */
    public void updateAnggota(Anggota anggota) {
        view.showLoading(true);
        
        SwingWorker<Void, Void> worker = new SwingWorker<>() {
            @Override
            protected Void doInBackground() throws Exception {
                anggotaService.updateAnggota(anggota);
                return null;
            }
            
            @Override
            protected void done() {
                view.showLoading(false);
                try {
                    get(); // Check for exception
                    MessageUtil.showSuccess(view, "Data anggota berhasil diupdate!");
                    loadData(); // Refresh table
                } catch (Exception e) {
                    MessageUtil.showError(view, e.getMessage());
                }
            }
        };
        worker.execute();
    }
    
    /**
     * Hapus anggota
     */
    public void deleteAnggota(int id, String nama) {
        // Konfirmasi delete
        if (!MessageUtil.showDeleteConfirm(view, "anggota \"" + nama + "\"")) {
            return;
        }
        
        view.showLoading(true);
        
        SwingWorker<Void, Void> worker = new SwingWorker<>() {
            @Override
            protected Void doInBackground() throws Exception {
                anggotaService.deleteAnggota(id);
                return null;
            }
            
            @Override
            protected void done() {
                view.showLoading(false);
                try {
                    get(); // Check for exception
                    MessageUtil.showSuccess(view, "Data anggota berhasil dihapus!");
                    loadData(); // Refresh table
                } catch (Exception e) {
                    MessageUtil.showError(view, e.getMessage());
                }
            }
        };
        worker.execute();
    }
    
    /**
     * Login anggota
     */
    public Anggota login(String email, String password) throws Exception {
        return anggotaService.login(email, password);
    }
}