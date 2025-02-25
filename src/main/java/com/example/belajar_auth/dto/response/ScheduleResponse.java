package com.example.belajar_auth.dto.response;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

/**
 * DTO untuk response data jadwal perjalanan.
 */
@Data
@Builder
public class ScheduleResponse {
    private Long id; // ID jadwal perjalanan
    private String destination; // Tujuan perjalanan
    private LocalDate departureDate; // Tanggal keberangkatan
    private LocalTime departureTime; // Waktu keberangkatan
    private Integer quota; // Kuota penumpang tersedia
    private BigDecimal price; // Harga tiket
    private LocalDateTime createdAt; // Waktu pembuatan data
    private LocalDateTime updatedAt; // Waktu terakhir diperbarui
}
