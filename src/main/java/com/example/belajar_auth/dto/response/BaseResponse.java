package com.example.belajar_auth.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Builder;
import lombok.Data;

/**
 * DTO untuk membungkus respons API dengan format yang konsisten.
 *
 * @param <T> Tipe data yang akan dikembalikan (generic)
 */
@Data // Lombok: Membuat getter, setter, toString, equals, dan hashCode otomatis
@Builder // Lombok: Memungkinkan pembuatan objek dengan pola builder
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class) // Menggunakan snake_case dalam JSON
@JsonInclude(JsonInclude.Include.NON_NULL) // Hanya sertakan field yang tidak null dalam JSON
public class BaseResponse<T> {

    private Boolean success; // Status sukses/gagal
    private String message; // Pesan respons
    private T data; // Data yang dikembalikan (generic)
    private ErrorResponse errors; // Detail error (jika ada)
    private PagingResponse paging; // Informasi pagination (jika ada)

}
