package com.komunitaslari.service;

import com.komunitaslari.model.Pengumuman;
import java.util.List;

public interface PengumumanService {
    List<Pengumuman> getAllPengumuman() throws Exception;
    List<Pengumuman> getRecentPengumuman(int limit) throws Exception;
    List<Pengumuman> searchPengumuman(String keyword) throws Exception;
    Pengumuman getPengumumanById(int id) throws Exception;
    boolean savePengumuman(Pengumuman pengumuman) throws Exception;
    boolean updatePengumuman(Pengumuman pengumuman) throws Exception;
    boolean deletePengumuman(int id) throws Exception;
    int getTotalPengumuman() throws Exception;
}