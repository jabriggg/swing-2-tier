package com.komunitaslari.model;

import java.time.LocalDateTime;

/**
 * Model/Entity untuk Pengumuman
 */
public class Pengumuman {
    
    private Integer id;
    private String judul;
    private String isi;
    private LocalDateTime tanggalKirim;
    private String pengirim;
    private LocalDateTime createdAt;
    
    // Constructor kosong
    public Pengumuman() {
        this.tanggalKirim = LocalDateTime.now();
        this.pengirim = "Admin"; // Default sender
    }
    
    // Constructor dengan parameter
    public Pengumuman(String judul, String isi) {
        this();
        this.judul = judul;
        this.isi = isi;
    }
    
    // Constructor lengkap
    public Pengumuman(Integer id, String judul, String isi, LocalDateTime tanggalKirim, String pengirim) {
        this.id = id;
        this.judul = judul;
        this.isi = isi;
        this.tanggalKirim = tanggalKirim;
        this.pengirim = pengirim;
    }
    
    // Getters and Setters
    public Integer getId() {
        return id;
    }
    
    public void setId(Integer id) {
        this.id = id;
    }
    
    public String getJudul() {
        return judul;
    }
    
    public void setJudul(String judul) {
        this.judul = judul;
    }
    
    public String getIsi() {
        return isi;
    }
    
    public void setIsi(String isi) {
        this.isi = isi;
    }
    
    public LocalDateTime getTanggalKirim() {
        return tanggalKirim;
    }
    
    public void setTanggalKirim(LocalDateTime tanggalKirim) {
        this.tanggalKirim = tanggalKirim;
    }
    
    public String getPengirim() {
        return pengirim;
    }
    
    public void setPengirim(String pengirim) {
        this.pengirim = pengirim;
    }
    
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
    
    @Override
    public String toString() {
        return "Pengumuman{" +
                "id=" + id +
                ", judul='" + judul + '\'' +
                ", tanggalKirim=" + tanggalKirim +
                ", pengirim='" + pengirim + '\'' +
                '}';
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        
        Pengumuman that = (Pengumuman) o;
        return id != null && id.equals(that.id);
    }
    
    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}