package com.example.belajar_auth.services.impl;

import com.example.belajar_auth.services.ValidationService;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service // Menandakan bahwa kelas ini adalah sebuah Service dalam Spring
public class ValidationServiceImpl implements ValidationService {

    @Autowired
    private Validator validator; // Objek Validator yang digunakan untuk melakukan validasi

    @Override
    public void validate(Object request) {
        // Melakukan validasi terhadap objek request
        Set<ConstraintViolation<Object>> constraintViolations = validator.validate(request);

        // Jika ada pelanggaran validasi, lemparkan ConstraintViolationException
        if (!constraintViolations.isEmpty()) {
            throw new ConstraintViolationException(constraintViolations);
        }
    }
}
