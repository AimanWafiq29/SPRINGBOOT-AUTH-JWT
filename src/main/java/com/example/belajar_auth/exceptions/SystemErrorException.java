package com.example.belajar_auth.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Custom exception untuk menangani error 500 (Internal Server Error).
 */
@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class SystemErrorException extends RuntimeException {

    // Konstruktor menerima Exception sebagai parameter
    public SystemErrorException(Exception e) {
        super("Terjadi Kesalahan pada sistem"); // Pesan default
        e.printStackTrace(); // Cetak stack trace ke log
    }
}
