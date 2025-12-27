package com.komunitaslari.model;

import java.time.LocalDateTime;

/**
 * Model/Entity untuk relasi Peserta dan Event
 */
public class PesertaEvent {
    
    private Integer id;
    private Integer idAnggota;
    private Integer idEvent;
    private LocalDateTime tanggalDaftar;
    private LocalDateTime createdAt;
    
    // Data tambahan dari JOIN (tidak disimpan di database)
    private String namaAnggota;
    private String emailAnggota;
    private String namaEvent;
    
    // Constructor kosong
    public PesertaEvent() {
        this.tanggalDaftar = LocalDateTime.now();
    }
    
    // Constructor dengan parameter
    public PesertaEvent(Integer idAnggota, Integer idEvent) {
        this();
        this.idAnggota = idAnggota;
        this.idEvent = idEvent;
    }
    
    // Constructor lengkap
    public PesertaEvent(Integer id, Integer idAnggota, Integer idEvent, LocalDateTime tanggalDaftar) {
        this.id = id;
        this.idAnggota = idAnggota;
        this.idEvent = idEvent;
        this.tanggalDaftar = tanggalDaftar;
    }
    
    // Getters and Setters
    public Integer getId() {
        return id;
    }
    
    public void setId(Integer id) {
        this.id = id;
    }
    
    public Integer getIdAnggota() {
        return idAnggota;
    }
    
    public void setIdAnggota(Integer idAnggota) {
        this.idAnggota = idAnggota;
    }
    
    public Integer getIdEvent() {
        return idEvent;
    }
    
    public void setIdEvent(Integer idEvent) {
        this.idEvent = idEvent;
    }
    
    public LocalDateTime getTanggalDaftar() {
        return tanggalDaftar;
    }
    
    public void setTanggalDaftar(LocalDateTime tanggalDaftar) {
        this.tanggalDaftar = tanggalDaftar;
    }
    
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
    
    public String getNamaAnggota() {
        return namaAnggota;
    }
    
    public void setNamaAnggota(String namaAnggota) {
        this.namaAnggota = namaAnggota;
    }
    
    public String getEmailAnggota() {
        return emailAnggota;
    }
    
    public void setEmailAnggota(String emailAnggota) {
        this.emailAnggota = emailAnggota;
    }
    
    public String getNamaEvent() {
        return namaEvent;
    }
    
    public void setNamaEvent(String namaEvent) {
        this.namaEvent = namaEvent;
    }
    
    @Override
    public String toString() {
        return "PesertaEvent{" +
                "id=" + id +
                ", idAnggota=" + idAnggota +
                ", idEvent=" + idEvent +
                ", tanggalDaftar=" + tanggalDaftar +
                '}';
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        
        PesertaEvent that = (PesertaEvent) o;
        return id != null && id.equals(that.id);
    }
    
    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}