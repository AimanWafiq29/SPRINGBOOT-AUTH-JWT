package com.example.belajar_auth.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Custom exception untuk menangani kesalahan dengan status 404 Not Found.
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
public class NotFoundException extends RuntimeException {

    // Konstruktor dengan pesan error
    public NotFoundException(String message) {
        super(message);
    }
}
