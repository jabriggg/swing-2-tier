package com.komunitaslari.controller;

import com.komunitaslari.model.Pengumuman;
import com.komunitaslari.util.MessageUtil;
import com.komunitaslari.view.PengumumanPanel;
import com.komunitaslari.worker.pengumuman.*;

import javax.swing.*;
import java.util.List;

/**
 * Controller untuk handle user actions pada Pengumuman
 */
public class PengumumanController {
    
    private final PengumumanPanel view;
    
    public PengumumanController(PengumumanPanel view) {
        this.view = view;
    }
    
    /**
     * Load semua data pengumuman
     */
    public void loadData() {
        view.showLoading(true);
        
        LoadPengumumanWorker worker = new LoadPengumumanWorker();
        worker.addPropertyChangeListener(evt -> {
            if ("state".equals(evt.getPropertyName())) {
                if (SwingWorker.StateValue.DONE == evt.getNewValue()) {
                    try {
                        List<Pengumuman> data = worker.get();
                        view.updateTable(data);
                        view.showLoading(false);
                    } catch (Exception e) {
                        view.showLoading(false);
                        MessageUtil.showError(view, "Gagal memuat data: " + e.getMessage());
                    }
                }
            }
        });
        worker.execute();
    }
    
    /**
     * Search pengumuman berdasarkan keyword
     */
    public void searchData(String keyword) {
        view.showLoading(true);
        
        LoadPengumumanWorker worker = new LoadPengumumanWorker(keyword);
        worker.addPropertyChangeListener(evt -> {
            if ("state".equals(evt.getPropertyName())) {
                if (SwingWorker.StateValue.DONE == evt.getNewValue()) {
                    try {
                        List<Pengumuman> data = worker.get();
                        view.updateTable(data);
                        view.showLoading(false);
                    } catch (Exception e) {
                        view.showLoading(false);
                        MessageUtil.showError(view, "Gagal mencari data: " + e.getMessage());
                    }
                }
            }
        });
        worker.execute();
    }
    
    /**
     * Simpan pengumuman baru (mode save biasa)
     */
    public void savePengumuman(Pengumuman pengumuman) {
        view.showLoading(true);
        
        SavePengumumanWorker worker = new SavePengumumanWorker(pengumuman, false);
        worker.addPropertyChangeListener(evt -> {
            if ("state".equals(evt.getPropertyName())) {
                if (SwingWorker.StateValue.DONE == evt.getNewValue()) {
                    view.showLoading(false);
                    
                    if (worker.hasError()) {
                        MessageUtil.showError(view, worker.getError().getMessage());
                    } else {
                        try {
                            int newId = worker.get(); // Dapatkan ID baru
                            MessageUtil.showSuccess(view, "Pengumuman berhasil disimpan! (ID: " + newId + ")");
                            loadData(); // Refresh table
                        } catch (Exception e) {
                            MessageUtil.showError(view, "Error: " + e.getMessage());
                        }
                    }
                }
            }
        });
        worker.execute();
    }
    
    /**
     * Update pengumuman yang sudah ada
     */
    public void updatePengumuman(Pengumuman pengumuman) {
        view.showLoading(true);
        
        UpdatePengumumanWorker worker = new UpdatePengumumanWorker(pengumuman);
        worker.addPropertyChangeListener(evt -> {
            if ("state".equals(evt.getPropertyName())) {
                if (SwingWorker.StateValue.DONE == evt.getNewValue()) {
                    view.showLoading(false);
                    
                    if (worker.hasError()) {
                        MessageUtil.showError(view, worker.getError().getMessage());
                    } else {
                        try {
                            boolean success = worker.get();
                            if (success) {
                                MessageUtil.showSuccess(view, "Pengumuman berhasil diperbarui!");
                                loadData(); // Refresh table
                            } else {
                                MessageUtil.showError(view, "Gagal memperbarui pengumuman!");
                            }
                        } catch (Exception e) {
                            MessageUtil.showError(view, "Error: " + e.getMessage());
                        }
                    }
                }
            }
        });
        worker.execute();
    }
    
    /**
     * Broadcast pengumuman ke semua anggota (simulasi realtime)
     */
    public void broadcastPengumuman(Pengumuman pengumuman) {
        // Konfirmasi broadcast
        String message = "Apakah Anda yakin ingin mengirim pengumuman ini ke semua anggota?\n\n" +
                        "Judul: " + pengumuman.getJudul() + "\n" +
                        "Isi: " + (pengumuman.getIsi().length() > 50 ? 
                                  pengumuman.getIsi().substring(0, 50) + "..." : 
                                  pengumuman.getIsi());
        
        if (!MessageUtil.showConfirm(view, message)) {
            return;
        }
        
        view.showLoading(true);
        
        SavePengumumanWorker worker = new SavePengumumanWorker(pengumuman, true);
        worker.addPropertyChangeListener(evt -> {
            if ("state".equals(evt.getPropertyName())) {
                if (SwingWorker.StateValue.DONE == evt.getNewValue()) {
                    view.showLoading(false);
                    
                    if (worker.hasError()) {
                        MessageUtil.showError(view, worker.getError().getMessage());
                    } else {
                        try {
                            int newId = worker.get();
                            MessageUtil.showSuccess(view, 
                                "✅ Pengumuman berhasil dikirim ke semua anggota! (ID: " + newId + ")\n\n" +
                                "Simulasi: Dalam aplikasi real, pengumuman akan dikirim via:\n" +
                                "• WebSocket (realtime notification)\n" +
                                "• Email notification\n" +
                                "• Push notification"
                            );
                            loadData(); // Refresh table
                        } catch (Exception e) {
                            MessageUtil.showError(view, "Error: " + e.getMessage());
                        }
                    }
                }
            }
        });
        worker.execute();
    }
    
    /**
     * Hapus pengumuman
     */
    public void deletePengumuman(int id, String judul) {
        // Konfirmasi delete
        if (!MessageUtil.showDeleteConfirm(view, "pengumuman \"" + judul + "\"")) {
            return;
        }
        
        view.showLoading(true);
        
        DeletePengumumanWorker worker = new DeletePengumumanWorker(id);
        worker.addPropertyChangeListener(evt -> {
            if ("state".equals(evt.getPropertyName())) {
                if (SwingWorker.StateValue.DONE == evt.getNewValue()) {
                    view.showLoading(false);
                    
                    if (worker.hasError()) {
                        MessageUtil.showError(view, worker.getError().getMessage());
                    } else {
                        try {
                            boolean success = worker.get();
                            if (success) {
                                MessageUtil.showSuccess(view, "Pengumuman berhasil dihapus!");
                                loadData(); // Refresh table
                            } else {
                                MessageUtil.showError(view, "Gagal menghapus pengumuman!");
                            }
                        } catch (Exception e) {
                            MessageUtil.showError(view, "Error: " + e.getMessage());
                        }
                    }
                }
            }
        });
        worker.execute();
    }
    
    /**
     * Lihat detail pengumuman lengkap
     */
    public void showDetail(Pengumuman pengumuman) {
        if (pengumuman == null) {
            return;
        }
        
        // Format detail untuk ditampilkan
        String detail = String.format(
            "═══════════════════════════════════════\n" +
            "DETAIL PENGUMUMAN\n" +
            "═══════════════════════════════════════\n\n" +
            "Judul:\n%s\n\n" +
            "Isi:\n%s\n\n" +
            "Pengirim: %s\n" +
            "Tanggal Kirim: %s\n" +
            "═══════════════════════════════════════",
            pengumuman.getJudul(),
            pengumuman.getIsi(),
            pengumuman.getPengirim(),
            com.komunitaslari.util.DateUtil.format(pengumuman.getTanggalKirim())
        );
        
        // Tampilkan dalam dialog dengan scrollable text area
        JTextArea textArea = new JTextArea(detail);
        textArea.setEditable(false);
        textArea.setWrapStyleWord(true);
        textArea.setLineWrap(true);
        textArea.setFont(new java.awt.Font("SansSerif", java.awt.Font.PLAIN, 12));
        
        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setPreferredSize(new java.awt.Dimension(500, 300));
        
        JOptionPane.showMessageDialog(
            view,
            scrollPane,
            "Detail Pengumuman",
            JOptionPane.INFORMATION_MESSAGE
        );
    }
}