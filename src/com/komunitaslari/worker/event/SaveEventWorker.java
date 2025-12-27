package com.komunitaslari.worker.event;

import com.komunitaslari.model.Event;
import com.komunitaslari.service.EventService;
import com.komunitaslari.service.impl.EventServiceImpl;
import com.komunitaslari.worker.BaseWorker;

public class SaveEventWorker extends BaseWorker<Boolean, Void> {
    private final EventService service = new EventServiceImpl();
    private final Event event;

    public SaveEventWorker(Event event) {
        this.event = event;
    }

    @Override
    protected Boolean doInBackground() throws Exception {
        try {
            return service.saveEvent(event);
        } catch (Exception e) {
            this.error = e;
            return false;
        }
    }
}