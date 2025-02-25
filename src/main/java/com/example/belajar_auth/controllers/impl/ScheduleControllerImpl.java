package com.example.belajar_auth.controllers.impl;

import com.example.belajar_auth.annotations.BaseControllerImpl;
import com.example.belajar_auth.controllers.ScheduleController;
import com.example.belajar_auth.dto.request.CreateScheduleRequest;
import com.example.belajar_auth.dto.request.UpdateScheduleRequest;
import com.example.belajar_auth.dto.response.BaseResponse;
import com.example.belajar_auth.dto.response.ScheduleResponse;
import com.example.belajar_auth.services.ScheduleService;
import com.example.belajar_auth.utils.ResponseHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Implementasi dari {@link ScheduleController} yang berisi logika bisnis
 * untuk mengelola jadwal keberangkatan.
 * <p>
 * Menggunakan anotasi {@link BaseControllerImpl} untuk menyederhanakan
 * deklarasi controller, yang secara otomatis sudah memiliki anotasi
 * {@link Controller}, {@link ResponseBody}, dan {@link CrossOrigin}.
 * </p>
 */
@BaseControllerImpl() // Anotasi khusus yang sudah mencakup fitur dasar controller
@RequiredArgsConstructor // Menggunakan Lombok untuk membuat constructor otomatis
public class ScheduleControllerImpl implements ScheduleController {

    @Autowired
    private ScheduleService scheduleService; // Menghubungkan service yang menangani jadwal

    /**
     * Mengambil daftar semua jadwal keberangkatan yang tersedia.
     *
     * @return BaseResponse berisi daftar jadwal dalam bentuk JSON
     */
    @Override
    public BaseResponse list() {
        List<ScheduleResponse> schedules = scheduleService.list(); // Mengambil semua jadwal
        return ResponseHelper.createBaseResponse(schedules); // Mengembalikan respons dalam format JSON
    }

    /**
     * Mengambil detail jadwal berdasarkan ID tertentu.
     *
     * @param id ID jadwal yang akan dicari
     * @return BaseResponse berisi data detail jadwal
     */
    @Override
    public BaseResponse getById(@PathVariable Long id) {
        ScheduleResponse schedule = scheduleService.getById(id); // Mencari jadwal berdasarkan ID
        return ResponseHelper.createBaseResponse(schedule); // Mengembalikan respons dengan data jadwal
    }

    /**
     * Membuat jadwal keberangkatan baru berdasarkan data yang dikirim.
     *
     * @param request Data jadwal yang akan dibuat
     * @return BaseResponse berisi hasil operasi dengan data jadwal yang baru dibuat
     */
    @Override
    public BaseResponse create(@RequestBody CreateScheduleRequest request) {
        ScheduleResponse newSchedule = scheduleService.create(request); // Menyimpan jadwal baru
        return ResponseHelper.createBaseResponse(newSchedule); // Mengembalikan hasil operasi dalam JSON
    }

    /**
     * Memperbarui jadwal keberangkatan berdasarkan ID yang diberikan.
     *
     * @param id ID jadwal yang akan diperbarui
     * @param request Data baru yang akan diperbarui
     * @return BaseResponse berisi hasil operasi dengan data jadwal yang sudah diperbarui
     */
    @Override
    public BaseResponse update(@PathVariable Long id, @RequestBody UpdateScheduleRequest request) {
        ScheduleResponse updatedSchedule = scheduleService.update(id, request); // Memperbarui data jadwal
        return ResponseHelper.createBaseResponse(updatedSchedule); // Mengembalikan hasil operasi dalam JSON
    }

    /**
     * Menghapus jadwal keberangkatan berdasarkan ID tertentu.
     *
     * @param id ID jadwal yang akan dihapus
     * @return BaseResponse berisi konfirmasi bahwa jadwal berhasil dihapus
     */
    @Override
    public BaseResponse delete(@PathVariable Long id) {
        scheduleService.delete(id); // Menghapus jadwal berdasarkan ID
        return ResponseHelper.createBaseResponse("Schedule deleted successfully"); // Mengembalikan pesan sukses
    }
}
