package com.komunitaslari.model;

import java.time.LocalDateTime;

/**
 * Model/Entity untuk Event Lari
 */
public class Event {
    
    private Integer id;
    private String namaEvent;
    private String deskripsi;
    private String lokasi;
    private LocalDateTime tanggalEvent;
    private Integer kuota;
    private String status; // Misal: "OPEN", "CLOSED", "DONE"
    private LocalDateTime createdAt;

    // Constructor kosong
    public Event() {
        this.status = "OPEN";
        this.createdAt = LocalDateTime.now();
    }

    // Constructor untuk insert baru (minimal data)
    public Event(String namaEvent, String lokasi, LocalDateTime tanggalEvent, Integer kuota) {
        this();
        this.namaEvent = namaEvent;
        this.lokasi = lokasi;
        this.tanggalEvent = tanggalEvent;
        this.kuota = kuota;
    }

    // Constructor lengkap (biasanya dipakai DAO saat load data)
    public Event(Integer id, String namaEvent, String deskripsi, String lokasi, 
                 LocalDateTime tanggalEvent, Integer kuota, String status) {
        this.id = id;
        this.namaEvent = namaEvent;
        this.deskripsi = deskripsi;
        this.lokasi = lokasi;
        this.tanggalEvent = tanggalEvent;
        this.kuota = kuota;
        this.status = status;
    }

    // Getters and Setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNamaEvent() {
        return namaEvent;
    }

    public void setNamaEvent(String namaEvent) {
        this.namaEvent = namaEvent;
    }

    public String getDeskripsi() {
        return deskripsi;
    }

    public void setDeskripsi(String deskripsi) {
        this.deskripsi = deskripsi;
    }

    public String getLokasi() {
        return lokasi;
    }

    public void setLokasi(String lokasi) {
        this.lokasi = lokasi;
    }

    public LocalDateTime getTanggalEvent() {
        return tanggalEvent;
    }

    public void setTanggalEvent(LocalDateTime tanggalEvent) {
        this.tanggalEvent = tanggalEvent;
    }

    public Integer getKuota() {
        return kuota;
    }

    public void setKuota(Integer kuota) {
        this.kuota = kuota;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public String toString() {
        return "Event{" +
                "id=" + id +
                ", namaEvent='" + namaEvent + '\'' +
                ", lokasi='" + lokasi + '\'' +
                ", tanggalEvent=" + tanggalEvent +
                ", status='" + status + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Event event = (Event) o;
        return id != null && id.equals(event.id);
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}