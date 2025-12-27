package com.komunitaslari.service;

import com.komunitaslari.model.Event;
import java.util.List;

public interface EventService {
    List<Event> getAllEvents() throws Exception;
    List<Event> searchEvents(String keyword) throws Exception;
    List<Event> getEventsByStatus(String status) throws Exception;
    Event getEventById(int id) throws Exception;
    boolean saveEvent(Event event) throws Exception;
    boolean updateEvent(Event event) throws Exception;
    boolean deleteEvent(int id) throws Exception;
    int getTotalEvents() throws Exception;
    int getTotalEventsByStatus(String status) throws Exception;
    boolean updateEventStatus(int id, String status) throws Exception;
}