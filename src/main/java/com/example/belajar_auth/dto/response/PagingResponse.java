package com.example.belajar_auth.dto.response;

import lombok.Builder;
import lombok.Data;

/**
 * DTO untuk menangani informasi pagination dalam API respons.
 */
@Data // Lombok: Membuat getter, setter, toString, equals, dan hashCode otomatis
@Builder // Lombok: Memungkinkan pembuatan objek dengan pola builder
public class PagingResponse {
    private Integer currentPage; // Halaman saat ini yang sedang diakses
    private Integer totalPages; // Total halaman yang tersedia
    private Long totalElements; // Jumlah total elemen/data
    private Integer pageSize; // Jumlah data per halaman
}
