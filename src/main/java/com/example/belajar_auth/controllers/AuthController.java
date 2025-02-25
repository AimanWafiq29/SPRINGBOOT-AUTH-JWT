package com.example.belajar_auth.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.example.belajar_auth.annotations.BaseController;
import com.example.belajar_auth.annotations.PublicAccess;
import com.example.belajar_auth.dto.request.RequestSignIn;
import com.example.belajar_auth.dto.request.RequestSignUp;
import com.example.belajar_auth.dto.response.BaseResponse;

/**
 * Controller untuk autentikasi pengguna.
 *
 * Semua endpoint dalam controller ini bersifat public dan tidak memerlukan autentikasi awal.
 */
@BaseController("auth") // Custom annotation yang menandakan bahwa ini adalah controller untuk "auth"
public interface AuthController { // Menggunakan interface agar bisa diimplementasikan oleh beberapa kelas

    /**
     * Endpoint untuk mengecek apakah server berjalan dengan baik.
     *
     * @return BaseResponse dengan status server
     */
    @PublicAccess // Menandakan bahwa endpoint ini bisa diakses tanpa autentikasi
    @GetMapping("ping") // Mapping URL "/auth/ping"
    BaseResponse ping();

    /**
     * Endpoint untuk mendaftarkan pengguna baru.
     *
     * @param req Data pengguna untuk pendaftaran (dikirim dalam request body)
     * @return BaseResponse hasil registrasi
     */
    @PublicAccess
    @PostMapping("v1/user/sign-up") // Mapping URL "/auth/v1/user/sign-up"
    BaseResponse signUp(@RequestBody RequestSignUp req);

    /**
     * Endpoint untuk login pengguna.
     *
     * @param req Data login pengguna (dikirim dalam request body)
     * @return BaseResponse dengan token atau informasi login
     */
    @PublicAccess
    @PostMapping("v1/user/sign-in") // Mapping URL "/auth/v1/user/sign-in"
    BaseResponse signInUser(@RequestBody RequestSignIn req);
}
