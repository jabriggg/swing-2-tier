package com.komunitaslari.service.impl;

import com.komunitaslari.dao.EventDAO;
import com.komunitaslari.dao.mysql.EventDAOImpl; // Tambahkan import ini
import com.komunitaslari.model.Event;
import com.komunitaslari.service.EventService;
import java.time.LocalDateTime;
import java.util.List;

public class EventServiceImpl implements EventService {
    
    private final EventDAO eventDAO;
    
    public EventServiceImpl() {
        // PERBAIKAN: Instansiasi ke class Implementasi, bukan Interface
        this.eventDAO = new EventDAOImpl(); 
    }
    
    @Override
    public List<Event> getAllEvents() throws Exception {
        return eventDAO.findAll();
    }
    
    @Override
    public List<Event> searchEvents(String keyword) throws Exception {
        if (keyword == null || keyword.trim().isEmpty()) return getAllEvents();
        return eventDAO.search(keyword.trim());
    }

    @Override
    public boolean saveEvent(Event event) throws Exception {
        validateEvent(event);
        return eventDAO.create(event) > 0; // Perbaikan: create mengembalikan ID (int)
    }
    
    @Override
    public boolean updateEvent(Event event) throws Exception {
        validateEvent(event);
        return eventDAO.update(event);
    }
    
    @Override
    public boolean deleteEvent(int id) throws Exception {
        return eventDAO.delete(id);
    }

    // Method pembantu validasi
    private void validateEvent(Event event) throws Exception {
        if (event == null) throw new Exception("Data event kosong");
        if (event.getNamaEvent() == null || event.getNamaEvent().isEmpty()) 
            throw new Exception("Nama event wajib diisi");
        if (event.getKuota() <= 0) 
            throw new Exception("Kuota minimal 1");
    }

    // Implementasikan method interface lainnya (findById, count, dll) sesuai kebutuhan DAO Anda
    @Override public Event getEventById(int id) throws Exception { return null; }
    @Override public List<Event> getEventsByStatus(String status) throws Exception { return null; }
    @Override public int getTotalEvents() throws Exception { return 0; }
    @Override public int getTotalEventsByStatus(String status) throws Exception { return 0; }
    @Override public boolean updateEventStatus(int id, String status) throws Exception { return false; }
}