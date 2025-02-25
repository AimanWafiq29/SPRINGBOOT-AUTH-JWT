package com.example.belajar_auth.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;

/**
 * DTO (Data Transfer Object) untuk membuat jadwal keberangkatan baru.
 *
 * Menggunakan anotasi validasi untuk memastikan data yang dikirim tidak kosong.
 * Juga menggunakan Lombok untuk mengurangi boilerplate code.
 */
@Data // Lombok: Menghasilkan getter, setter, toString, equals, dan hashCode secara otomatis
@Builder // Lombok: Memungkinkan pembuatan objek menggunakan pola Builder
@NoArgsConstructor // Lombok: Membuat constructor tanpa parameter
@AllArgsConstructor // Lombok: Membuat constructor dengan semua parameter
public class CreateScheduleRequest {

    @NotNull(message = "Tujuan tidak boleh kosong") // Validasi: tidak boleh null
    private String destination; // Tujuan perjalanan (misalnya: Jakarta - Surabaya)

    @NotNull(message = "Tanggal keberangkatan tidak boleh kosong") // Validasi: tidak boleh null
    private LocalDate departureDate; // Tanggal keberangkatan dalam format YYYY-MM-DD

    @NotNull(message = "Waktu keberangkatan tidak boleh kosong") // Validasi: tidak boleh null
    private LocalTime departureTime; // Waktu keberangkatan dalam format HH:MM:SS

    @NotNull(message = "Kuota tidak boleh kosong") // Validasi: tidak boleh null
    private Integer quota; // Kuota penumpang yang tersedia untuk perjalanan ini

    @NotNull(message = "Harga tidak boleh kosong") // Validasi: tidak boleh null
    private BigDecimal price; // Harga tiket dalam bentuk desimal (misalnya: 150000.00)
}
