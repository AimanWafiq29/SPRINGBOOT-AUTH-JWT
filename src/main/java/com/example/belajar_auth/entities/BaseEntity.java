package com.example.belajar_auth.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicUpdate;

import java.time.Instant;
import java.util.UUID;

@MappedSuperclass // Menandakan bahwa kelas ini adalah superclass untuk entity lainnya
@Getter
@Setter
@DynamicUpdate // Hanya memperbarui kolom yang berubah saat melakukan update
public class BaseEntity {

    @Id
    @Column(name = "id")
    private String id; // Menggunakan UUID sebagai primary key

    @Column(name = "active")
    private Boolean active; // Menandakan status aktif/tidak aktif entitas

    @Column(name = "created_by", nullable = false)
    private String createdBy; // Menyimpan informasi siapa yang membuat entitas

    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt = Instant.now(); // Waktu pembuatan entitas (tidak dapat diubah)

    @Column(name = "updated_by")
    private String updatedBy; // Menyimpan informasi siapa yang terakhir memperbarui entitas

    @Column(name = "updated_at")
    private Instant updatedAt; // Waktu terakhir entitas diperbarui

    @Column(name = "deleted_by")
    private String deletedBy; // Menyimpan informasi siapa yang menghapus entitas

    @Column(name = "deleted_at")
    private Instant deletedAt; // Waktu penghapusan entitas (soft delete)

    /**
     * Method yang dijalankan sebelum entitas disimpan ke database.
     * - Mengenerate UUID jika id belum ada.
     * - Mengatur `active` ke `true`.
     * - Mengatur `createdAt` dan `updatedAt` ke waktu saat ini.
     */
    @PrePersist
    public void prePersist() {
        if (this.id == null) {
            this.id = UUID.randomUUID().toString(); // Generate UUID sebagai id
        }
        this.active = true; // Set status aktif ke true
        this.createdAt = Instant.now(); // Set waktu pembuatan
        this.updatedAt = Instant.now(); // Set waktu pembaruan
    }

    /**
     * Method yang dijalankan sebelum entitas diperbarui di database.
     * - Mengatur `updatedAt` ke waktu saat ini.
     */
    @PreUpdate
    public void preUpdate() {
        this.updatedAt = Instant.now(); // Update waktu pembaruan
    }

    /**
     * Method yang dijalankan sebelum entitas dihapus dari database.
     * - Mengatur `deletedAt` ke waktu saat ini.
     * - Mengatur `active` ke `false` (soft delete).
     */
    @PreRemove
    public void preRemove() {
        this.deletedAt = Instant.now(); // Set waktu penghapusan
        this.active = false; // Set status aktif ke false
    }
}