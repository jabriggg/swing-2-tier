package com.komunitaslari.service.impl;

import com.komunitaslari.dao.AnggotaDAO;
import com.komunitaslari.dao.EventDAO;
import com.komunitaslari.dao.mysql.AnggotaDAOImpl;
import com.komunitaslari.dao.mysql.EventDAOImpl;
import com.komunitaslari.model.Anggota;
import com.komunitaslari.service.DashboardService;
import java.util.HashMap;
import java.util.Map;

public class DashboardServiceImpl implements DashboardService {
    private final AnggotaDAO anggotaDAO = new AnggotaDAOImpl();
    private final EventDAO eventDAO = new EventDAOImpl();

    @Override
    public Map<String, Object> getDashboardData() throws Exception {
        Map<String, Object> data = new HashMap<>();
        
        // Mengambil data statistik dari database
        data.put("totalAnggota", anggotaDAO.countAll());
        data.put("anggotaAktif", anggotaDAO.countByStatus(Anggota.StatusAnggota.AKTIF));
        data.put("totalEvent", eventDAO.countAll());
        data.put("eventAktif", eventDAO.countByStatus("AKTIF"));
        
        return data;
    }

    @Override
    public Map<String, Object> getAnggotaStats() throws Exception {
        return getDashboardData();
    }

    @Override
    public Map<String, Object> getEventStats() throws Exception {
        return getDashboardData();
    }

    @Override
    public Map<String, Object> getUpcomingEvents() throws Exception {
        Map<String, Object> data = new HashMap<>();
        data.put("events", eventDAO.findAll());
        return data;
    }

    @Override
    public Map<String, Object> getRecentPengumuman() throws Exception {
        return new HashMap<>();
    }
}