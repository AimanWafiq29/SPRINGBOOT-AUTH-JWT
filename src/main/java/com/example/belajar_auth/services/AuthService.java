package com.example.belajar_auth.services;

import com.example.belajar_auth.dto.request.RequestSignIn;
import com.example.belajar_auth.dto.request.RequestSignUp;
import com.example.belajar_auth.dto.response.ResponseSignIn;

// Interface untuk layanan autentikasi
public interface AuthService {

    // Metode untuk melakukan pengecekan apakah layanan berjalan (ping)
    public String ping();

    // Metode untuk mendaftarkan pengguna baru
    String registerUser(RequestSignUp requestSignUp);

    // Metode untuk login pengguna dan mengembalikan respons login
    ResponseSignIn loginUser(RequestSignIn requestLogin);
}
