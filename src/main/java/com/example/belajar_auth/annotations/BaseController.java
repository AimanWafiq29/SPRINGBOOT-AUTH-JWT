package com.example.belajar_auth.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.core.annotation.AliasFor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * Anotasi kustom untuk menyederhanakan deklarasi controller RESTful
 * yang secara otomatis menyertakan response body dan memiliki request mapping bawaan.
 * <p>
 * Anotasi ini menggabungkan fungsi dari {@link RestController}, {@link ResponseBody},
 * dan {@link RequestMapping} menjadi satu anotasi tunggal.
 * </p>
 */
@Target(ElementType.TYPE) // Anotasi ini hanya dapat digunakan pada kelas (type level).
@Retention(RetentionPolicy.RUNTIME) // Anotasi ini akan tetap tersedia saat runtime.
@ResponseBody // Mengindikasikan bahwa nilai yang dikembalikan dari metode harus langsung digunakan sebagai response body.
@RestController // Menjadikan kelas sebagai controller REST.
@RequestMapping // Menyediakan konfigurasi mapping URL bawaan.
public @interface BaseController {

    /**
     * Alias untuk atribut {@link RequestMapping#name()}.
     * Digunakan untuk mendefinisikan nama controller dalam request mapping.
     *
     * @return nama dari controller
     */
    @AliasFor(annotation = RequestMapping.class)
    String name() default "";

    /**
     * Alias untuk atribut {@link RequestMapping#value()}.
     * Digunakan untuk menentukan path URL untuk controller ini.
     *
     * @return path URL untuk controller
     */
    @AliasFor(annotation = RequestMapping.class)
    String value() default "";
}
