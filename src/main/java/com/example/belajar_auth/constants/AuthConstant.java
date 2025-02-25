package com.example.belajar_auth.constants;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component // Menandakan bahwa kelas ini adalah sebuah Spring Bean yang bisa digunakan di seluruh aplikasi
@Getter // Lombok otomatis menghasilkan getter untuk variabel yang bersifat private/final
public class AuthConstant {

    // Konstanta untuk nama header HTTP yang digunakan dalam autentikasi
    public static String HEADER_X_WHO = "x_who";  // Menyimpan identitas pengguna
    public static String HEADER_X_ROLE = "x_role"; // Menyimpan peran (role) pengguna

    @Value("${auth.secret}") // Mengambil nilai dari properti di application.properties atau application.yml
    private String secret; // Kunci rahasia untuk JWT atau mekanisme keamanan lainnya

    @Value("${auth.expiration_time}") // Mengambil nilai dari konfigurasi untuk waktu kedaluwarsa token
    public long EXPIRATION_TIME; // Waktu kedaluwarsa token (misalnya dalam milidetik)
}
