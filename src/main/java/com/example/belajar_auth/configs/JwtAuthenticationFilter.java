package com.example.belajar_auth.configs;

import com.example.belajar_auth.constants.AuthConstant;
import com.example.belajar_auth.dto.response.BaseResponse;
import com.example.belajar_auth.services.JwtService;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * Filter untuk memproses autentikasi berbasis JWT.
 * Filter ini akan dieksekusi sekali untuk setiap request.
 */
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    // Service untuk memproses JWT
    private final JwtService jwtServiceImpl;

    // Service untuk memuat data pengguna berdasarkan username/email
    private final UserDetailsService userDetailsService;

    // Konstanta yang berisi konfigurasi autentikasi
    private final AuthConstant authConstant;

    /**
     * Metode yang menangani filtering request untuk memeriksa apakah JWT valid atau tidak.
     * Jika valid, akan menetapkan user sebagai pengguna yang sudah diautentikasi.
     */
    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain)
            throws IOException {

        try {
            System.out.println("=== Incoming Request: " + request.getRequestURI() + " ===");

            // Mengambil header Authorization dari request
            final String authHeader = request.getHeader("Authorization");
            System.out.println("Authorization Header: " + authHeader);

            final String jwt;
            final String userEmail;

            // Jika header Authorization tidak ada atau tidak dimulai dengan "Bearer ", lanjutkan request tanpa autentikasi
            if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                System.out.println("JWT tidak ditemukan atau format tidak valid. Melanjutkan ke filter berikutnya.");
                filterChain.doFilter(request, response);
                return;
            }

            // Mengambil token JWT dari header Authorization
            jwt = authHeader.substring(7);
            System.out.println("Extracted JWT: " + jwt);

            // Mengekstrak email/username dari token JWT
            userEmail = jwtServiceImpl.extractUsername(jwt);
            System.out.println("Extracted User Email: " + userEmail);

            // Jika email tidak null dan pengguna belum diautentikasi, lakukan validasi token
            if (userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                // Memuat data pengguna berdasarkan email/username yang diperoleh dari JWT
                UserDetails userDetails = this.userDetailsService.loadUserByUsername(userEmail);
                System.out.println("Loaded UserDetails: " + userDetails.getUsername());

                // Validasi token JWT
                if (jwtServiceImpl.isTokenValid(jwt, userDetails)) {
                    System.out.println("JWT valid, menetapkan autentikasi pengguna.");

                    // Membuat objek autentikasi Spring Security
                    UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                            userDetails, null, userDetails.getAuthorities()
                    );
                    authToken.setDetails(
                            new WebAuthenticationDetailsSource().buildDetails(request)
                    );

                    // Mengambil secret key dari konfigurasi
                    String secret = authConstant.getSecret();
                    System.out.println("Secret key: " + secret);

                    // Mengekstrak informasi dari JWT menggunakan secret key
                    Claims claims = Jwts.parser()
                            .setSigningKey(authConstant.getSecret())
                            .parseClaimsJws(jwt)
                            .getBody();

                    // Mengambil user ID dari claim dalam JWT
                    String userId = claims.get(AuthConstant.HEADER_X_WHO, String.class);
                    System.out.println("Extracted User ID from JWT: " + userId);

                    // Menyimpan user ID ke dalam request agar bisa digunakan oleh controller
                    request.setAttribute(AuthConstant.HEADER_X_WHO, userId);

                    // Menetapkan autentikasi ke dalam SecurityContextHolder
                    SecurityContextHolder.getContext().setAuthentication(authToken);
                } else {
                    System.out.println("JWT tidak valid!");
                }
            } else {
                System.out.println("User email null atau sudah diautentikasi.");
            }

            // Melanjutkan request ke filter berikutnya
            filterChain.doFilter(request, response);
        } catch (Exception ex) {
            System.out.println("Error dalam filter JWT: " + ex.getMessage());
            ex.printStackTrace();
            createHttpUnAuthentication(response, ex.getMessage());
        }
    }

    /**
     * Mengirimkan respons HTTP 401 Unauthorized jika terjadi kesalahan autentikasi.
     */
    private void createHttpUnAuthentication(HttpServletResponse httpServletResponse, String message) throws IOException {
        // Membuat objek response dalam format JSON menggunakan BaseResponse
        ObjectMapper mapper = new ObjectMapper();
        BaseResponse response = BaseResponse.builder()
                .success(false)
                .message(message)
                .build();

        // Mengonversi response menjadi JSON
        String json = mapper.writeValueAsString(response);

        // Mengatur response HTTP menjadi JSON dengan status 401 (Unauthorized)
        httpServletResponse.setContentType(MediaType.APPLICATION_JSON_VALUE);
        httpServletResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        httpServletResponse.getWriter().write(json);
    }

    /**
     * Menentukan apakah filter ini harus dijalankan dalam permintaan async.
     * Jika `false`, berarti filter tetap berjalan dalam permintaan async.
     */
    @Override
    protected boolean shouldNotFilterAsyncDispatch() {
        return false;
    }

    /**
     * Menentukan apakah filter ini harus dijalankan dalam permintaan error dispatch.
     * Jika `false`, berarti filter tetap berjalan dalam skenario error.
     */
    @Override
    protected boolean shouldNotFilterErrorDispatch() {
        return false;
    }
}
