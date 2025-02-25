package com.example.belajar_auth.utils;

import com.example.belajar_auth.entities.BaseEntity;

import java.time.Instant;
import java.util.UUID;

/**
 * Utility class untuk operasi terkait entity seperti pembuatan, pembaruan, dan penghapusan entity.
 */
public class EntityUtils {

    /**
     * Menghasilkan UUID baru dalam bentuk string.
     *
     * @return UUID dalam bentuk string
     */
    public static String generateUUID() {
        return UUID.randomUUID().toString();
    }

    /**
     * Menginisialisasi entity baru dengan ID unik, metadata penciptaan, dan waktu penciptaan.
     *
     * @param entity Entity yang akan dibuat
     * @param userId ID pengguna yang membuat entity
     * @param <T> Tipe entity yang merupakan turunan dari BaseEntity
     * @return Entity yang telah diperbarui dengan metadata penciptaan
     */
    public static <T extends BaseEntity> T created(T entity, String userId) {
        // Validasi bahwa userId tidak kosong atau null
        if (userId == null || userId.isBlank()) {
            throw new IllegalArgumentException("User ID tidak boleh kosong atau null.");
        }

        Instant now = Instant.now();

        // Jika ID entity belum ada, buat ID baru
        if (entity.getId() == null || entity.getId().isBlank()) {
            entity.setId(generateUUID());
        }
        // Set user yang membuat entity jika belum ada
        if (entity.getCreatedBy() == null || entity.getCreatedBy().isBlank()) {
            entity.setCreatedBy(userId);
        }
        // Set waktu pembuatan entity jika belum ada
        if (entity.getCreatedAt() == null) {
            entity.setCreatedAt(now);
        }
        // Set waktu pembaruan entity jika belum ada
        if (entity.getUpdatedAt() == null) {
            entity.setUpdatedAt(now);
        }
        return entity;
    }

    /**
     * Memperbarui metadata entity dengan informasi pengguna yang melakukan pembaruan.
     *
     * @param entity Entity yang akan diperbarui
     * @param userId ID pengguna yang memperbarui entity
     * @param <T> Tipe entity yang merupakan turunan dari BaseEntity
     * @return Entity yang telah diperbarui dengan metadata pembaruan
     */
    public static <T extends BaseEntity> T update(T entity, String userId) {
        // Validasi bahwa userId tidak kosong atau null
        if (userId == null || userId.isBlank()) {
            throw new IllegalArgumentException("User ID tidak boleh kosong atau null.");
        }

        entity.setUpdatedBy(userId);
        entity.setUpdatedAt(Instant.now());
        return entity;
    }

    /**
     * Menandai entity sebagai dihapus dengan menyetel metadata penghapusan.
     *
     * @param entity Entity yang akan dihapus
     * @param userId ID pengguna yang menghapus entity
     * @param <T> Tipe entity yang merupakan turunan dari BaseEntity
     * @return Entity yang telah diperbarui dengan metadata penghapusan
     */
    public static <T extends BaseEntity> T delete(T entity, String userId) {
        // Validasi bahwa userId tidak kosong atau null
        if (userId == null || userId.isBlank()) {
            throw new IllegalArgumentException("User ID tidak boleh kosong atau null.");
        }

        entity.setDeletedBy(userId);
        entity.setDeletedAt(Instant.now());
        entity.setActive(false); // Menonaktifkan entity
        return entity;
    }
}
