package com.example.belajar_auth.annotations;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Anotasi kustom yang menggabungkan anotasi {@link Controller}, {@link ResponseBody},
 * dan {@link CrossOrigin} untuk mempermudah pengelolaan CORS serta respons RESTful
 * dalam controller Spring.
 * <p>
 * Anotasi ini sebaiknya digunakan pada kelas yang menangani permintaan HTTP dan
 * mengembalikan respons langsung dalam format JSON atau jenis media lainnya
 * dengan dukungan CORS yang sudah diaktifkan.
 * </p>
 * <p>
 * Secara default, anotasi ini mengizinkan semua asal permintaan (origins)
 * dan semua header. Konfigurasi CORS dapat disesuaikan sesuai kebutuhan.
 * </p>
 */
@Target(ElementType.TYPE) // Anotasi ini hanya dapat digunakan pada level kelas.
@Retention(RetentionPolicy.RUNTIME) // Anotasi ini tetap tersedia saat runtime.
@Documented // Menandakan bahwa anotasi ini harus disertakan dalam dokumentasi Javadoc.
@Controller // Menjadikan kelas sebagai controller Spring.
@ResponseBody // Semua metode dalam kelas ini akan mengembalikan data langsung sebagai respons HTTP.
@CrossOrigin(origins = "*", allowedHeaders = "*") // Mengaktifkan CORS dengan izin semua origin dan header.
public @interface BaseControllerImpl {

    /**
     * Mengizinkan penyesuaian asal permintaan (origins) untuk CORS.
     * Secara default, nilainya adalah "*" (mengizinkan semua origins).
     *
     * @return daftar origins yang diizinkan untuk CORS.
     */
    String[] origins() default "*";

    /**
     * Mengizinkan penyesuaian header yang diizinkan dalam permintaan CORS.
     * Secara default, nilainya adalah "*" (mengizinkan semua header).
     *
     * @return daftar header yang diizinkan untuk CORS.
     */
    String[] allowedHeaders() default "*";
}
