package com.komunitaslari.controller;

import com.komunitaslari.model.Event;
import com.komunitaslari.util.MessageUtil;
import com.komunitaslari.view.EventPanel;
import com.komunitaslari.worker.event.*;

import javax.swing.*;

/**
 * Controller untuk handle user actions pada Event
 */
public class EventController {
    
    private final EventPanel view;
    
    public EventController(EventPanel view) {
        this.view = view;
    }
    
    /**
     * Load semua data event
     */
    public void loadData() {
        view.showLoading(true);
        
        LoadEventWorker worker = new LoadEventWorker();
        worker.addPropertyChangeListener(evt -> {
            if ("state".equals(evt.getPropertyName()) && 
                SwingWorker.StateValue.DONE == evt.getNewValue()) {
                try {
                    view.updateTable(worker.get());
                    view.showLoading(false);
                } catch (Exception e) {
                    view.showLoading(false);
                    MessageUtil.showError(view, "Gagal memuat data: " + e.getMessage());
                }
            }
        });
        worker.execute();
    }
    
    /**
     * Search event berdasarkan keyword
     */
    public void searchData(String keyword) {
        view.showLoading(true);
        
        LoadEventWorker worker = new LoadEventWorker(keyword);
        worker.addPropertyChangeListener(evt -> {
            if ("state".equals(evt.getPropertyName()) && 
                SwingWorker.StateValue.DONE == evt.getNewValue()) {
                try {
                    view.updateTable(worker.get());
                    view.showLoading(false);
                } catch (Exception e) {
                    view.showLoading(false);
                    MessageUtil.showError(view, "Gagal mencari data: " + e.getMessage());
                }
            }
        });
        worker.execute();
    }
    
    /**
     * Load event berdasarkan status
     */
    public void loadByStatus(String status) {
        view.showLoading(true);
        
        LoadEventWorker worker = new LoadEventWorker(status, true);
        worker.addPropertyChangeListener(evt -> {
            if ("state".equals(evt.getPropertyName()) && 
                SwingWorker.StateValue.DONE == evt.getNewValue()) {
                try {
                    view.updateTable(worker.get());
                    view.showLoading(false);
                } catch (Exception e) {
                    view.showLoading(false);
                    MessageUtil.showError(view, "Gagal memuat data: " + e.getMessage());
                }
            }
        });
        worker.execute();
    }
    
    /**
     * Simpan event baru
     */
    public void saveEvent(Event event) {
        view.showLoading(true);
        
        SaveEventWorker worker = new SaveEventWorker(event);
        worker.addPropertyChangeListener(evt -> {
            if ("state".equals(evt.getPropertyName()) && 
                SwingWorker.StateValue.DONE == evt.getNewValue()) {
                view.showLoading(false);
                
                if (worker.hasError()) {
                    MessageUtil.showError(view, worker.getError().getMessage());
                } else {
                    MessageUtil.showSuccess(view, "Event berhasil disimpan!");
                    loadData(); // Refresh table
                }
            }
        });
        worker.execute();
    }
    
    /**
     * Update data event
     */
    public void updateEvent(Event event) {
        view.showLoading(true);
        
        UpdateEventWorker worker = new UpdateEventWorker(event);
        worker.addPropertyChangeListener(evt -> {
            if ("state".equals(evt.getPropertyName()) && 
                SwingWorker.StateValue.DONE == evt.getNewValue()) {
                view.showLoading(false);
                
                if (worker.hasError()) {
                    MessageUtil.showError(view, worker.getError().getMessage());
                } else {
                    MessageUtil.showSuccess(view, "Event berhasil diupdate!");
                    loadData(); // Refresh table
                }
            }
        });
        worker.execute();
    }
    
    /**
     * Hapus event
     */
    public void deleteEvent(int id, String namaEvent) {
        // Konfirmasi delete
        if (!MessageUtil.showDeleteConfirm(view, "event \"" + namaEvent + "\"")) {
            return;
        }
        
        view.showLoading(true);
        
        DeleteEventWorker worker = new DeleteEventWorker(id);
        worker.addPropertyChangeListener(evt -> {
            if ("state".equals(evt.getPropertyName()) && 
                SwingWorker.StateValue.DONE == evt.getNewValue()) {
                view.showLoading(false);
                
                if (worker.hasError()) {
                    MessageUtil.showError(view, worker.getError().getMessage());
                } else {
                    MessageUtil.showSuccess(view, "Event berhasil dihapus!");
                    loadData(); // Refresh table
                }
            }
        });
        worker.execute();
    }
    
    /**
     * Update status event (OPEN, CLOSED, DONE)
     */
    public void updateStatus(int id, String status) {
        view.showLoading(true);
        
        UpdateEventWorker worker = new UpdateEventWorker(id, status);
        worker.addPropertyChangeListener(evt -> {
            if ("state".equals(evt.getPropertyName()) && 
                SwingWorker.StateValue.DONE == evt.getNewValue()) {
                view.showLoading(false);
                
                if (worker.hasError()) {
                    MessageUtil.showError(view, worker.getError().getMessage());
                } else {
                    MessageUtil.showSuccess(view, "Status event berhasil diupdate!");
                    loadData(); // Refresh table
                }
            }
        });
        worker.execute();
    }
}