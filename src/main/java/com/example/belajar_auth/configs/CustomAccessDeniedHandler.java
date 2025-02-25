package com.example.belajar_auth.configs;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * Komponen yang menangani akses yang ditolak oleh Spring Security.
 * Kelas ini akan digunakan ketika pengguna mencoba mengakses resource
 * yang tidak memiliki izin.
 */
@Component
public class CustomAccessDeniedHandler implements AccessDeniedHandler {

    // Pesan error yang akan ditampilkan saat akses ditolak
    private static final String UNAUTHORIZED_ACCESS_MESSAGE = "Unauthorized Access: ";

    // Kode status HTTP yang akan dikembalikan (401 - Unauthorized)
    private static final int UNAUTHORIZED_STATUS_CODE = HttpServletResponse.SC_UNAUTHORIZED;

    /**
     * Method ini akan dieksekusi saat pengguna mencoba mengakses resource
     * tanpa izin yang sesuai.
     *
     * @param request Objek HttpServletRequest yang berisi informasi request dari client
     * @param response Objek HttpServletResponse yang digunakan untuk mengirimkan respons ke client
     * @param accessDeniedException Exception yang berisi informasi tentang penyebab akses ditolak
     * @throws IOException Jika terjadi kesalahan saat menulis respons ke client
     */
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response,
                       org.springframework.security.access.AccessDeniedException accessDeniedException) throws IOException {

        // (Opsional) Logging untuk mencatat kejadian akses yang ditolak
        // Misalnya, bisa menggunakan library logging seperti SLF4J atau Log4J
        // log.warn("Akses ditolak untuk {}: {}", request.getRequestURI(), accessDeniedException.getMessage());

        // Mengatur status HTTP menjadi 401 (Unauthorized)
        response.setStatus(UNAUTHORIZED_STATUS_CODE);

        // Menulis pesan error ke dalam respons yang akan dikirim ke client
        response.getWriter().write(UNAUTHORIZED_ACCESS_MESSAGE + accessDeniedException.getMessage());
    }
}
