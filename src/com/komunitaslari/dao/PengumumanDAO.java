package com.komunitaslari.dao;

import com.komunitaslari.model.Pengumuman;
import java.sql.SQLException;
import java.util.List;

/**
 * Interface DAO untuk operasi database Pengumuman
 */
public interface PengumumanDAO {
    
    /**
     * Menyimpan pengumuman baru ke database
     * @param pengumuman Object pengumuman yang akan disimpan
     * @return ID pengumuman yang baru disimpan
     * @throws SQLException jika terjadi error database
     */
    int create(Pengumuman pengumuman) throws SQLException;
    
    /**
     * Mengambil semua data pengumuman dari database
     * @return List berisi semua pengumuman
     * @throws SQLException jika terjadi error database
     */
    List<Pengumuman> findAll() throws SQLException;
    
    /**
     * Mengambil data pengumuman berdasarkan ID
     * @param id ID pengumuman yang dicari
     * @return Object Pengumuman jika ditemukan, null jika tidak
     * @throws SQLException jika terjadi error database
     */
    Pengumuman findById(int id) throws SQLException;
    
    /**
     * Mengupdate data pengumuman
     * @param pengumuman Object pengumuman dengan data terbaru
     * @return true jika update berhasil, false jika gagal
     * @throws SQLException jika terjadi error database
     */
    boolean update(Pengumuman pengumuman) throws SQLException;
    
    /**
     * Menghapus pengumuman dari database
     * @param id ID pengumuman yang akan dihapus
     * @return true jika delete berhasil, false jika gagal
     * @throws SQLException jika terjadi error database
     */
    boolean delete(int id) throws SQLException;
    
    /**
     * Mencari pengumuman berdasarkan keyword (judul atau isi)
     * @param keyword Kata kunci pencarian
     * @return List pengumuman yang sesuai dengan keyword
     * @throws SQLException jika terjadi error database
     */
    List<Pengumuman> search(String keyword) throws SQLException;
    
    /**
     * Mengambil pengumuman terbaru
     * @param limit Jumlah maksimal pengumuman yang diambil
     * @return List pengumuman terbaru
     * @throws SQLException jika terjadi error database
     */
    List<Pengumuman> findLatest(int limit) throws SQLException;
    
    /**
     * Mengambil pengumuman berdasarkan pengirim
     * @param pengirim Nama pengirim
     * @return List pengumuman dari pengirim tertentu
     * @throws SQLException jika terjadi error database
     */
    List<Pengumuman> findByPengirim(String pengirim) throws SQLException;
    
    /**
     * Menghitung total semua pengumuman
     * @return Total jumlah pengumuman
     * @throws SQLException jika terjadi error database
     */
    int countAll() throws SQLException;
}