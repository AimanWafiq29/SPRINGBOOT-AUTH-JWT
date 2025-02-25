package com.example.belajar_auth.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Anotasi untuk menandai metode atau kelas yang dapat diakses secara publik.
 * <p>
 * Metode atau kelas yang diberi anotasi ini dapat diakses tanpa autentikasi
 * atau otorisasi. Biasanya digunakan untuk endpoint yang tidak memerlukan
 * login, seperti pemeriksaan kesehatan (health check) atau pengambilan
 * informasi publik.
 * </p>
 */
@Target({ElementType.METHOD, ElementType.TYPE}) // Anotasi ini dapat digunakan pada metode atau kelas.
@Retention(RetentionPolicy.RUNTIME) // Anotasi ini akan tetap tersedia saat runtime.
public @interface PublicAccess {
}
