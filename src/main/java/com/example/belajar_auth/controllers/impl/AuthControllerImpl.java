package com.example.belajar_auth.controllers.impl;

import com.example.belajar_auth.annotations.BaseControllerImpl;
import com.example.belajar_auth.controllers.AuthController;
import com.example.belajar_auth.dto.request.RequestSignIn;
import com.example.belajar_auth.dto.request.RequestSignUp;
import com.example.belajar_auth.dto.response.BaseResponse;
import com.example.belajar_auth.services.AuthService;
import com.example.belajar_auth.utils.ResponseHelper;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * Implementasi dari {@link AuthController} yang berfungsi untuk menangani proses autentikasi pengguna.
 * <p>
 * Kelas ini mengimplementasikan semua metode yang ada di interface `AuthController`,
 * sehingga saat ada permintaan ke API autentikasi, akan ditangani di sini.
 * </p>
 */
@BaseControllerImpl  // Anotasi ini kemungkinan merupakan anotasi khusus untuk menandai controller ini
@RequiredArgsConstructor  // Lombok akan otomatis membuat constructor untuk field yang memiliki `final`
public class AuthControllerImpl implements AuthController {

    // Service yang digunakan untuk memproses logika bisnis autentikasi
    private final AuthService authService;

    /**
     * Endpoint untuk mengecek apakah server berjalan dengan baik.
     * <p>
     * Metode ini akan dipanggil ketika ada request ke `/auth/ping`.
     * Jika server berjalan normal, maka akan mengembalikan respons sukses.
     * </p>
     *
     * @return BaseResponse yang berisi status server
     */
    @Override
    public BaseResponse ping() {
        return ResponseHelper.createBaseResponse(authService.ping());
    }

    /**
     * Endpoint untuk mendaftarkan pengguna baru.
     * <p>
     * Data pengguna yang dikirim dalam request body akan divalidasi sebelum diproses.
     * Jika data valid, pengguna baru akan didaftarkan melalui `authService.registerUser()`
     * </p>
     *
     * @param req Objek `RequestSignUp` yang berisi data pengguna untuk registrasi
     * @return BaseResponse hasil registrasi, misalnya sukses atau gagal
     */
    @Override
    public BaseResponse signUp(@Valid @RequestBody RequestSignUp req) {
        return ResponseHelper.createBaseResponse(authService.registerUser(req));
    }

    /**
     * Endpoint untuk login pengguna.
     * <p>
     * Metode ini menerima data login dalam bentuk `RequestSignIn`,
     * kemudian diteruskan ke `authService.loginUser()` untuk diproses.
     * Jika berhasil, pengguna akan mendapatkan token sebagai tanda autentikasi.
     * </p>
     *
     * @param req Objek `RequestSignIn` yang berisi username/email dan password
     * @return BaseResponse yang berisi token atau informasi login lainnya
     */
    @Override
    public BaseResponse signInUser(@Valid @RequestBody RequestSignIn req) {
        return ResponseHelper.createBaseResponse(authService.loginUser(req));
    }
}
