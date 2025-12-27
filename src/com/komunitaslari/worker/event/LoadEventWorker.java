package com.komunitaslari.worker.event;

import com.komunitaslari.model.Event;
import com.komunitaslari.service.EventService;
import com.komunitaslari.service.impl.EventServiceImpl;
import com.komunitaslari.worker.BaseWorker;
import java.util.List;

public class LoadEventWorker extends BaseWorker<List<Event>, Void> {
    private final EventService service = new EventServiceImpl();
    private String keyword;
    private String status;
    private boolean isStatusFilter = false;

    public LoadEventWorker() {}

    public LoadEventWorker(String keyword) {
        this.keyword = keyword;
    }

    public LoadEventWorker(String status, boolean isStatusFilter) {
        this.status = status;
        this.isStatusFilter = isStatusFilter;
    }

    @Override
    protected List<Event> doInBackground() throws Exception {
        try {
            if (isStatusFilter) return service.getEventsByStatus(status);
            if (keyword != null) return service.searchEvents(keyword);
            return service.getAllEvents();
        } catch (Exception e) {
            this.error = e;
            return null;
        }
    }
}