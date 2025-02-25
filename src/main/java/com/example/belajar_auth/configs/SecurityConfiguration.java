package com.example.belajar_auth.configs;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfigurationSource;

@Configuration // Menandakan bahwa kelas ini adalah konfigurasi Spring
@RequiredArgsConstructor // Lombok akan menghasilkan konstruktor untuk field final yang ada di kelas ini
public class SecurityConfiguration {

    private final JwtAuthenticationFilter jwtAuthFilter; // Filter untuk memproses JWT
    private final AuthenticationProvider authenticationProvider; // Provider untuk autentikasi
    private final PublicApiScanner publicApiScanner; // Scanner untuk mendapatkan URL yang diizinkan tanpa autentikasi

    @Bean // Menandakan bahwa metode ini menghasilkan bean yang akan dikelola oleh Spring
    public SecurityFilterChain securityFilterChain(HttpSecurity http, CorsConfigurationSource corsConfigurationSource) throws Exception {
        // Mengambil daftar URL publik yang tidak memerlukan autentikasi
        String[] finalWhiteList = publicApiScanner.getPublicUrls().toArray(new String[0]);

        http
                // Konfigurasi manajemen sesi agar bersifat stateless (tidak menyimpan sesi pengguna)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                // Menetapkan provider autentikasi yang digunakan
                .authenticationProvider(authenticationProvider)
                // Menambahkan filter JWT sebelum filter autentikasi Username/Password bawaan Spring Security
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
                // Konfigurasi otorisasi untuk setiap request yang masuk
                .authorizeHttpRequests(req -> req
                        .requestMatchers(finalWhiteList) // Mengizinkan akses ke URL yang termasuk dalam daftar putih tanpa autentikasi
                        .permitAll()
                        .anyRequest() // Semua request lainnya harus diautentikasi
                        .authenticated())
                // Mengatur bagaimana sistem menangani error autentikasi (mengembalikan status 401 Unauthorized)
                .exceptionHandling(e -> e.authenticationEntryPoint(
                        new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED)))
                // Menonaktifkan CSRF karena API ini berbasis stateless JWT (CSRF biasanya digunakan untuk session-based authentication)
                .csrf(AbstractHttpConfigurer::disable)
                // Mengaktifkan konfigurasi CORS agar API bisa diakses dari domain yang berbeda
                .cors(cors -> cors.configurationSource(corsConfigurationSource));

        return http.build();
    }
}
