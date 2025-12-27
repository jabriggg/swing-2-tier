package com.komunitaslari.worker.event;

import com.komunitaslari.model.Event;
import com.komunitaslari.service.EventService;
import com.komunitaslari.service.impl.EventServiceImpl;
import com.komunitaslari.worker.BaseWorker;

/**
 * Worker untuk menangani update data event di background thread
 */
public class UpdateEventWorker extends BaseWorker<Boolean, Void> {
    
    private final EventService service = new EventServiceImpl();
    private Event event;
    private int id;
    private String status;
    private boolean isStatusUpdate = false;

    // Constructor untuk update data event lengkap
    public UpdateEventWorker(Event event) {
        this.event = event;
        this.isStatusUpdate = false;
    }

    // Constructor khusus untuk update status saja
    public UpdateEventWorker(int id, String status) {
        this.id = id;
        this.status = status;
        this.isStatusUpdate = true;
    }

    @Override
    protected Boolean doInBackground() throws Exception {
        try {
            if (isStatusUpdate) {
                // Memanggil service untuk update status berdasarkan ID
                return service.updateEventStatus(id, status);
            } else {
                // Memanggil service untuk update objek event lengkap
                return service.updateEvent(event);
            }
        } catch (Exception e) {
            this.error = e; // Menyimpan error ke BaseWorker jika terjadi masalah
            return false;
        }
    }
}