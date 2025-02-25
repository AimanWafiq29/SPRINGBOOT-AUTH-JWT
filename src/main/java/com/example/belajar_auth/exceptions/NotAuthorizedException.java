package com.example.belajar_auth.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Custom exception untuk menangani kesalahan dengan status 401 Unauthorized.
 */
@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class NotAuthorizedException extends RuntimeException {

    // Konstruktor dengan pesan error
    public NotAuthorizedException(String message) {
        super(message);
    }
}
