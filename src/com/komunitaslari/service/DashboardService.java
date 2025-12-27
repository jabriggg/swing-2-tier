package com.komunitaslari.service;

import java.util.Map;

public interface DashboardService {
    Map<String, Object> getDashboardData() throws Exception;
    Map<String, Object> getAnggotaStats() throws Exception; // Tambahkan ini
    Map<String, Object> getEventStats() throws Exception;   // Tambahkan ini
    Map<String, Object> getUpcomingEvents() throws Exception;
    Map<String, Object> getRecentPengumuman() throws Exception;
}