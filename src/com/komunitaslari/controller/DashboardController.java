package com.komunitaslari.controller;

import com.komunitaslari.util.MessageUtil;
import com.komunitaslari.view.DashboardPanel;
import com.komunitaslari.worker.dashboard.LoadDashboardWorker;

import javax.swing.*;
import java.util.Map;

/**
 * Controller untuk handle dashboard data
 */
public class DashboardController {
    
    private final DashboardPanel view;
    
    public DashboardController(DashboardPanel view) {
        this.view = view;
    }
    
    /**
     * Load semua data dashboard (statistik)
     */
    public void loadDashboardData() {
    System.out.println("Memulai refresh data dashboard..."); // Buat debug di terminal
    view.showLoading(true);
    
    // Pastikan LoadDashboardWorker lu ngambil data terbaru dari DAO
    LoadDashboardWorker worker = new LoadDashboardWorker();
    worker.addPropertyChangeListener(evt -> {
        if ("state".equals(evt.getPropertyName()) && 
            SwingWorker.StateValue.DONE == evt.getNewValue()) {
            try {
                Map<String, Object> data = worker.get();
                // DEBUG: Print data yang didapet dari database ke console VS Code
                System.out.println("Data dapet: " + data.toString()); 
                
                view.updateDashboard(data);
                view.showLoading(false);
                // Tambahin notifikasi biar lu tau ini sukses
                System.out.println("Dashboard sukses diperbarui!");
            } catch (Exception e) {
                view.showLoading(false);
                MessageUtil.showError(view, "Gagal: " + e.getMessage());
                e.printStackTrace();
            }
        }
    });
    worker.execute();
}
    
    /**
     * Refresh dashboard data
     */
    public void refreshData() {
        loadDashboardData();
    }
    
    /**
     * Load statistik anggota saja
     */
    public void loadAnggotaStats() {
        view.showLoading(true);
        
        LoadDashboardWorker worker = new LoadDashboardWorker("anggota");
        worker.addPropertyChangeListener(evt -> {
            if ("state".equals(evt.getPropertyName()) && 
                SwingWorker.StateValue.DONE == evt.getNewValue()) {
                try {
                    Map<String, Object> data = worker.get();
                    view.updateAnggotaStats(data);
                    view.showLoading(false);
                } catch (Exception e) {
                    view.showLoading(false);
                    MessageUtil.showError(view, "Gagal memuat statistik anggota: " + e.getMessage());
                }
            }
        });
        worker.execute();
    }
    
    /**
     * Load statistik event saja
     */
    public void loadEventStats() {
        view.showLoading(true);
        
        LoadDashboardWorker worker = new LoadDashboardWorker("event");
        worker.addPropertyChangeListener(evt -> {
            if ("state".equals(evt.getPropertyName()) && 
                SwingWorker.StateValue.DONE == evt.getNewValue()) {
                try {
                    Map<String, Object> data = worker.get();
                    view.updateEventStats(data);
                    view.showLoading(false);
                } catch (Exception e) {
                    view.showLoading(false);
                    MessageUtil.showError(view, "Gagal memuat statistik event: " + e.getMessage());
                }
            }
        });
        worker.execute();
    }
    
    /**
     * Load pengumuman terbaru untuk dashboard
     */
    public void loadRecentPengumuman() {
        view.showLoading(true);
        
        LoadDashboardWorker worker = new LoadDashboardWorker("pengumuman");
        worker.addPropertyChangeListener(evt -> {
            if ("state".equals(evt.getPropertyName()) && 
                SwingWorker.StateValue.DONE == evt.getNewValue()) {
                try {
                    Map<String, Object> data = worker.get();
                    view.updatePengumumanList(data);
                    view.showLoading(false);
                } catch (Exception e) {
                    view.showLoading(false);
                    MessageUtil.showError(view, "Gagal memuat pengumuman: " + e.getMessage());
                }
            }
        });
        worker.execute();
    }
    
    /**
     * Load event mendatang untuk dashboard
     */
    public void loadUpcomingEvents() {
        view.showLoading(true);
        
        LoadDashboardWorker worker = new LoadDashboardWorker("upcoming-events");
        worker.addPropertyChangeListener(evt -> {
            if ("state".equals(evt.getPropertyName()) && 
                SwingWorker.StateValue.DONE == evt.getNewValue()) {
                try {
                    Map<String, Object> data = worker.get();
                    view.updateEventList(data);
                    view.showLoading(false);
                } catch (Exception e) {
                    view.showLoading(false);
                    MessageUtil.showError(view, "Gagal memuat event: " + e.getMessage());
                }
            }
        });
        worker.execute();
    }
}