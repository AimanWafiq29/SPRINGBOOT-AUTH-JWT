package com.example.belajar_auth.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;

/**
 * DTO untuk permintaan pembaruan jadwal perjalanan.
 * <p>
 * Semua field bersifat opsional, memungkinkan pembaruan sebagian (partial update).
 * </p>
 */
@Data // Lombok: Membuat getter, setter, toString, equals, dan hashCode otomatis
@NoArgsConstructor // Lombok: Membuat constructor tanpa parameter
@AllArgsConstructor // Lombok: Membuat constructor dengan semua parameter
@Builder // Lombok: Memungkinkan pembuatan objek dengan pola builder
public class UpdateScheduleRequest {

    private String destination; // Tujuan perjalanan (opsional)

    private LocalDate departureDate; // Tanggal keberangkatan (opsional)

    private LocalTime departureTime; // Waktu keberangkatan (opsional)

    private Integer quota; // Kuota penumpang yang tersedia (opsional)

    private BigDecimal price; // Harga tiket (opsional)

}
