package com.example.belajar_auth.configs;

import lombok.Getter;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import com.example.belajar_auth.annotations.PublicAccess;

import java.util.HashSet;
import java.util.Set;

@Getter
@Component
public class PublicApiScanner {

    // Menyimpan daftar URL yang dapat diakses tanpa autentikasi
    private final Set<String> publicUrls;

    // Konstruktor yang akan dipanggil saat objek PublicApiScanner dibuat
    public PublicApiScanner(RequestMappingHandlerMapping requestMappingHandlerMapping) {
        this.publicUrls = new HashSet<>();

        // Melakukan iterasi pada semua handler method (endpoint) yang terdaftar
        requestMappingHandlerMapping.getHandlerMethods().forEach((requestMappingInfo, handlerMethod) -> {
            // Mengecek apakah metode ini memiliki anotasi @PublicAccess
            if (handlerMethod.getMethodAnnotation(PublicAccess.class) != null) {
                // Jika ada, tambahkan URL yang terkait ke dalam daftar publicUrls
                publicUrls.addAll(requestMappingInfo.getPatternValues());
            }
        });
    }
}
