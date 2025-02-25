package com.example.belajar_auth.dto.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.*;

/**
 * DTO (Data Transfer Object) untuk permintaan masuk (Sign In).
 * <p>
 * Kelas ini digunakan untuk mengirim data saat pengguna melakukan login
 * menggunakan email atau nomor telepon serta kata sandi.
 * </p>
 */
@Data // Lombok: Membuat getter, setter, toString, equals, dan hashCode secara otomatis
@Builder // Lombok: Memungkinkan pembuatan objek menggunakan pola Builder
@NoArgsConstructor // Lombok: Membuat constructor tanpa parameter
@AllArgsConstructor // Lombok: Membuat constructor dengan semua parameter
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class) // Mengubah nama properti menjadi snake_case saat diubah ke JSON
@JsonInclude(JsonInclude.Include.NON_NULL) // Hanya menyertakan field yang tidak null dalam JSON
public class RequestSignIn {

    @NonNull // Memastikan email tidak boleh null saat membuat objek
    private String email; // Email pengguna yang digunakan untuk masuk

    @NonNull // Memastikan nomor telepon tidak boleh null saat membuat objek
    private String phoneNumber; // Nomor telepon pengguna sebagai alternatif login

    @NonNull // Memastikan password tidak boleh null saat membuat objek
    private String password; // Kata sandi pengguna untuk autentikasi
}
