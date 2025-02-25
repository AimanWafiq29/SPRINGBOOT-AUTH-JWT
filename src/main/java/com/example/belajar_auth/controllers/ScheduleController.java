package com.example.belajar_auth.controllers;

import com.example.belajar_auth.annotations.BaseController;
import com.example.belajar_auth.dto.request.CreateScheduleRequest;
import com.example.belajar_auth.dto.request.UpdateScheduleRequest;
import com.example.belajar_auth.dto.response.BaseResponse;
import org.springframework.web.bind.annotation.*;

/**
 * Controller untuk mengelola jadwal keberangkatan.
 * <p>
 * Menggunakan anotasi kustom {@link BaseController} untuk menyederhanakan deklarasi controller RESTful.
 * </p>
 */
@BaseController("schedules") // Menjadikan ini sebagai controller dengan path dasar "/schedules"
public interface ScheduleController {

    /**
     * Endpoint untuk mendapatkan daftar semua jadwal keberangkatan.
     * <p>
     * Permintaan ini akan mengembalikan daftar semua jadwal yang ada dalam sistem.
     * </p>
     *
     * @return BaseResponse berisi daftar jadwal keberangkatan
     */
    @GetMapping
    BaseResponse list();

    /**
     * Endpoint untuk mendapatkan detail jadwal berdasarkan ID tertentu.
     * <p>
     * ID jadwal dikirim melalui path parameter (`/schedules/{id}`),
     * kemudian sistem akan mencari data berdasarkan ID tersebut.
     * </p>
     *
     * @param id ID jadwal yang akan dicari
     * @return BaseResponse berisi detail jadwal keberangkatan yang ditemukan
     */
    @GetMapping("/{id}")
    BaseResponse getById(@PathVariable Long id);

    /**
     * Endpoint untuk membuat jadwal keberangkatan baru.
     * <p>
     * Data jadwal dikirim dalam bentuk JSON melalui request body.
     * </p>
     *
     * @param request Objek `CreateScheduleRequest` yang berisi data jadwal baru
     * @return BaseResponse berisi hasil operasi (sukses/gagal)
     */
    @PostMapping("/create")
    BaseResponse create(@RequestBody CreateScheduleRequest request);

    /**
     * Endpoint untuk memperbarui data jadwal keberangkatan berdasarkan ID tertentu.
     * <p>
     * ID jadwal dikirim melalui path parameter (`/schedules/update/{id}`),
     * sedangkan data yang diperbarui dikirim melalui request body.
     * </p>
     *
     * @param id ID jadwal yang akan diperbarui
     * @param request Objek `UpdateScheduleRequest` yang berisi data baru untuk jadwal
     * @return BaseResponse berisi hasil operasi (sukses/gagal)
     */
    @PutMapping("/update/{id}")
    BaseResponse update(@PathVariable Long id, @RequestBody UpdateScheduleRequest request);

    /**
     * Endpoint untuk menghapus jadwal keberangkatan berdasarkan ID tertentu.
     * <p>
     * ID jadwal dikirim melalui path parameter (`/schedules/delete/{id}`),
     * kemudian sistem akan menghapus data berdasarkan ID tersebut.
     * </p>
     *
     * @param id ID jadwal yang akan dihapus
     * @return BaseResponse berisi hasil operasi (sukses/gagal)
     */
    @DeleteMapping("/delete/{id}")
    BaseResponse delete(@PathVariable Long id);
}
