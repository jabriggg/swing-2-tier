package com.komunitaslari.worker.event;

import com.komunitaslari.service.EventService;
import com.komunitaslari.service.impl.EventServiceImpl;
import com.komunitaslari.worker.BaseWorker;

public class DeleteEventWorker extends BaseWorker<Boolean, Void> {
    private final EventService service = new EventServiceImpl();
    private final int id;

    public DeleteEventWorker(int id) {
        this.id = id;
    }

    @Override
    protected Boolean doInBackground() throws Exception {
        try {
            return service.deleteEvent(id);
        } catch (Exception e) {
            this.error = e;
            return false;
        }
    }
}