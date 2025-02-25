package com.example.belajar_auth.dto.request;

import com.example.belajar_auth.enums.SignUpTypeEnum;
import jakarta.validation.constraints.*;
import lombok.*;

/**
 * DTO (Data Transfer Object) untuk permintaan pendaftaran akun (Sign Up).
 * <p>
 * Kelas ini digunakan untuk menangani data yang dikirim saat pengguna melakukan pendaftaran akun baru.
 * </p>
 */
@Getter // Lombok: Membuat getter untuk semua properti secara otomatis
@Setter // Lombok: Membuat setter untuk semua properti secara otomatis
@NoArgsConstructor // Lombok: Membuat constructor tanpa parameter
@AllArgsConstructor // Lombok: Membuat constructor dengan semua parameter
@Builder // Lombok: Memungkinkan pembuatan objek menggunakan pola Builder
public class RequestSignUp {

    @NotBlank(message = "Nama depan tidak boleh kosong") // Validasi: Tidak boleh kosong atau hanya spasi
    @Size(max = 50, message = "Nama depan maksimal 50 karakter") // Validasi: Maksimal 50 karakter
    private String firstName; // Nama depan pengguna

    @NotBlank(message = "Nama belakang tidak boleh kosong") // Validasi: Tidak boleh kosong atau hanya spasi
    @Size(max = 50, message = "Nama belakang maksimal 50 karakter") // Validasi: Maksimal 50 karakter
    private String lastName; // Nama belakang pengguna

    @NotBlank(message = "Email tidak boleh kosong") // Validasi: Tidak boleh kosong
    @Email(message = "Format email tidak valid") // Validasi: Harus dalam format email yang benar
    @Size(max = 100, message = "Email maksimal 100 karakter") // Validasi: Maksimal 100 karakter
    private String email; // Email pengguna

    @NotBlank(message = "Password tidak boleh kosong") // Validasi: Tidak boleh kosong
    @Size(min = 8, max = 50, message = "Password harus 8-50 karakter") // Validasi: Minimal 8, maksimal 50 karakter
    private String password; // Kata sandi pengguna

    @NotBlank(message = "Nomor telepon tidak boleh kosong") // Validasi: Tidak boleh kosong
    @Size(min = 10, max = 15, message = "Nomor telepon harus 10-15 karakter") // Validasi: Harus terdiri dari 10-15 karakter
    private String phoneNumber; // Nomor telepon pengguna

    @NotNull(message = "Tipe pendaftaran harus diisi") // Validasi: Tidak boleh null
    private SignUpTypeEnum signUpType; // Tipe pendaftaran (misalnya, EMAIL, GOOGLE, FACEBOOK)
}
