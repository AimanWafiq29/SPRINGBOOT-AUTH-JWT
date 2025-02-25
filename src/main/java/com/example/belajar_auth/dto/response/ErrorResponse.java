package com.example.belajar_auth.dto.response;

import lombok.Builder;
import lombok.Data;

/**
 * DTO untuk menangani respons error dalam API.
 */
@Data // Lombok: Membuat getter, setter, toString, equals, dan hashCode otomatis
@Builder // Lombok: Memungkinkan pembuatan objek dengan pola builder
public class ErrorResponse {
    private Integer code; // Kode error (misalnya, 400, 401, 404)
    private String title; // Judul error (misalnya, "Bad Request", "Unauthorized")
    private String message; // Pesan error yang lebih spesifik
    private String field; // Nama field yang error (opsional, untuk validasi)
}
