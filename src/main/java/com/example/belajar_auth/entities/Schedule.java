package com.example.belajar_auth.entities;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Entity
@Table(name = "travel_schedules") // Nama tabel menggunakan bentuk plural (konvensi umum)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Schedule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Auto-increment untuk primary key
    private Long id; // ID jadwal perjalanan

    @Column(name = "destination", nullable = false)
    private String destination; // Tujuan perjalanan

    @Column(name = "departure_date", nullable = false)
    private LocalDate departureDate; // Tanggal keberangkatan

    @Column(name = "departure_time", nullable = false)
    private LocalTime departureTime; // Waktu keberangkatan

    @Column(name = "quota", nullable = false)
    private Integer quota; // Kuota penumpang yang tersedia

    @Column(name = "price", nullable = false)
    private BigDecimal price; // Harga tiket

    @Column(name = "created_at", updatable = false, nullable = false)
    private LocalDateTime createdAt = LocalDateTime.now(); // Waktu pembuatan jadwal

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt = LocalDateTime.now(); // Waktu terakhir jadwal diperbarui

    /**
     * Method yang dijalankan sebelum entitas disimpan ke database.
     * - Mengatur `createdAt` dan `updatedAt` ke waktu saat ini.
     */
    @PrePersist
    public void prePersist() {
        LocalDateTime now = LocalDateTime.now();
        this.createdAt = now;
        this.updatedAt = now;
    }

    /**
     * Method yang dijalankan sebelum entitas diperbarui di database.
     * - Mengatur `updatedAt` ke waktu saat ini.
     */
    @PreUpdate
    public void preUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
}