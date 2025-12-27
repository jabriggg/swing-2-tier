package com.komunitaslari.worker.dashboard;

import com.komunitaslari.service.DashboardService;
import com.komunitaslari.service.impl.DashboardServiceImpl;
import javax.swing.*;
import java.util.Map;

public class LoadDashboardWorker extends SwingWorker<Map<String, Object>, Void> {
    private final DashboardService service;
    private String category = "all";
    private WorkerCallback callback;

    public interface WorkerCallback {
        void onStart();
        void onSuccess(Map<String, Object> data);
        void onError(Exception e);
    }

    public LoadDashboardWorker() {
        this.service = new DashboardServiceImpl();
    }

    public LoadDashboardWorker(String category) {
        this.service = new DashboardServiceImpl();
        this.category = category;
    }

    public LoadDashboardWorker(WorkerCallback callback) {
        this.service = new DashboardServiceImpl();
        this.callback = callback;
    }

    @Override
    protected Map<String, Object> doInBackground() throws Exception {
        // LOGIC BARU: Tarik data berdasarkan kategori yang diminta controller
        switch (category.toLowerCase()) {
            case "anggota":
                return service.getAnggotaStats();
            case "event":
                return service.getEventStats();
            case "upcoming-events":
                return service.getUpcomingEvents();
            case "pengumuman":
                return service.getRecentPengumuman();
            default:
                // Ini untuk dashboard utama (Total Anggota, Anggota Aktif, dll)
                return service.getDashboardData();
        }
    }

    @Override
    protected void done() {
        // Jika dipanggil dari Controller (pakai propertyChangeListener), 
        // logic-nya ada di Controller. Jika pakai callback, logic-nya di sini.
        if (callback != null) {
            try {
                callback.onSuccess(get());
            } catch (Exception e) {
                callback.onError(e);
            }
        }
    }
}