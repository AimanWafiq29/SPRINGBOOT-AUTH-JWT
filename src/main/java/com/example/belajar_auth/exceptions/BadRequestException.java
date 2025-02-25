package com.example.belajar_auth.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Custom exception untuk menangani kesalahan dengan status 400 Bad Request.
 */
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class BadRequestException extends RuntimeException {

    // Konstruktor dengan pesan error
    public BadRequestException(String message) {
        super(message);
    }
}
