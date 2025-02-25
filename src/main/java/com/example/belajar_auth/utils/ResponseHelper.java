package com.example.belajar_auth.utils;

import java.util.List;
import org.springframework.data.domain.Page;
import com.example.belajar_auth.dto.response.BaseResponse;
import com.example.belajar_auth.dto.response.PagingResponse;

/**
 * Kelas utilitas untuk membantu dalam pembuatan respons API yang terstruktur.
 */
public class ResponseHelper {

    /**
     * Membuat respons berbasis halaman (pagination).
     *
     * @param data Data dalam bentuk Page<T> (Spring Data Pagination)
     * @param <T>  Tipe data dalam respons
     * @return BaseResponse yang berisi data, informasi paging, dan status sukses
     */
    public static <T> BaseResponse<T> createBaseResponse(Page<T> data) {
        // Jika data null atau tidak memiliki konten, kembalikan respons dengan pesan "No data available"
        if (data == null || data.getContent() == null) {
            return createBaseResponse("No data available");
        }

        // Membuat objek PagingResponse untuk informasi halaman
        PagingResponse paging = PagingResponse.builder()
                .currentPage(data.getPageable().getPageNumber() + 1) // Halaman dimulai dari 0, jadi ditambah 1
                .totalPages(data.getTotalPages()) // Total halaman yang tersedia
                .totalElements(data.getTotalElements()) // Total elemen dalam seluruh halaman
                .pageSize(data.getSize()) // Ukuran per halaman
                .build();

        // Mengembalikan respons dengan data dan informasi paging
        return BaseResponse.<T>builder()
                .success(true)
                .message("Data retrieved successfully") // Pesan sukses
                .data((T) data.getContent()) // Data yang dikembalikan
                .paging(paging) // Informasi paging
                .build();
    }

    /**
     * Membuat respons berbasis daftar (List).
     *
     * @param data Data dalam bentuk List<T>
     * @param <T>  Tipe data dalam respons
     * @return BaseResponse yang berisi daftar data dan status sukses
     */
    public static <T> BaseResponse<List<T>> createBaseResponse(List<T> data) {
        return BaseResponse.<List<T>>builder()
                .success(!data.isEmpty()) // Jika data tidak kosong, sukses = true
                .message(!data.isEmpty() ? "Data retrieved successfully" : "No data found") // Pesan sesuai kondisi data
                .data(data) // Data yang dikembalikan
                .build();
    }

    /**
     * Membuat respons untuk satu objek data.
     *
     * @param data Data yang dikembalikan
     * @param <T>  Tipe data dalam respons
     * @return BaseResponse dengan data tunggal dan status sukses
     */
    public static <T> BaseResponse<T> createBaseResponse(T data) {
        return createBaseResponse(data, data != null ? "Data retrieved successfully" : "No data found");
    }

    /**
     * Membuat respons untuk satu objek data dengan pesan yang dapat dikustomisasi.
     *
     * @param data    Data yang dikembalikan
     * @param message Pesan yang ingin ditampilkan dalam respons
     * @param <T>     Tipe data dalam respons
     * @return BaseResponse dengan data, pesan, dan status sukses
     */
    public static <T> BaseResponse<T> createBaseResponse(T data, String message) {
        return BaseResponse.<T>builder()
                .success(data != null) // Jika data ada, sukses = true
                .message(message) // Menggunakan pesan yang diberikan
                .data(data) // Data yang dikembalikan
                .build();
    }

    /**
     * Membuat respons dengan hanya pesan (tanpa data).
     *
     * @param message Pesan yang ingin ditampilkan dalam respons
     * @param <T>     Tipe data dalam respons (tidak digunakan)
     * @return BaseResponse dengan pesan dan status sukses = false
     */
    public static <T> BaseResponse<T> createBaseResponse(String message) {
        return BaseResponse.<T>builder()
                .success(false) // Karena tidak ada data, sukses = false
                .message(message) // Menggunakan pesan yang diberikan
                .build();
    }
}
