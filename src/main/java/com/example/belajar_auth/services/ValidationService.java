package com.example.belajar_auth.services;

/**
 * Interface ValidationService digunakan untuk melakukan validasi terhadap objek request.
 */
public interface ValidationService {

    /**
     * Metode untuk memvalidasi objek request yang diberikan.
     * Jika validasi gagal, maka akan melemparkan ConstraintViolationException.
     *
     * @param request Objek yang akan divalidasi.
     */
    void validate(Object request);

}
