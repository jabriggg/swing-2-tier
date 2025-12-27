package com.komunitaslari.model;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Model/Entity untuk Anggota Komunitas - Versi Sinkron dengan Dialog
 */
public class Anggota {
    
    // Enum yang dibutuhin JComboBox di AnggotaDialog
    public enum StatusAnggota {
        AKTIF, NON_AKTIF
    }

    private Integer id;
    private String nama;
    private String email;
    private String password;
    private String telepon;
    // private String jenisKelamin;
    private String level; 
    private LocalDate tanggalDaftar; // Pake LocalDate biar sinkron sama DateUtil di Dialog
    private StatusAnggota status;    // Pake Enum biar sinkron sama JComboBox
    private LocalDateTime createdAt;

    public Anggota() {
        this.level = "USER";
        this.status = StatusAnggota.AKTIF; // Default
        this.tanggalDaftar = LocalDate.now();
        this.createdAt = LocalDateTime.now();
    }

    // Getter dan Setter yang WAJIB ada buat ngilangin error di Dialog:
    
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getNama() { return nama; }
    public void setNama(String nama) { this.nama = nama; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getTelepon() { return telepon; }
    public void setTelepon(String telepon) { this.telepon = telepon; }
    // public String getJenisKelamin() { return jenisKelamin; }
    // public void setJenisKelamin(String jenisKelamin) { this.jenisKelamin = jenisKelamin; }

    public LocalDate getTanggalDaftar() { return tanggalDaftar; }
    public void setTanggalDaftar(LocalDate tanggalDaftar) { this.tanggalDaftar = tanggalDaftar; }

    public StatusAnggota getStatus() { return status; }
    public void setStatus(StatusAnggota status) { this.status = status; }

    // Getter Setter tambahan buat keperluan DB/Login
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    
    public String getLevel() { return level; }
    public void setLevel(String level) { this.level = level; }
}