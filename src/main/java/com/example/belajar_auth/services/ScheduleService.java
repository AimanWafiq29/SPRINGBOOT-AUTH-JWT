package com.example.belajar_auth.services;

import com.example.belajar_auth.dto.request.CreateScheduleRequest;
import com.example.belajar_auth.dto.request.UpdateScheduleRequest;
import com.example.belajar_auth.dto.response.ScheduleResponse;

import java.util.List;

public interface ScheduleService {

    // Mengambil daftar semua jadwal yang tersedia
    List<ScheduleResponse> list();

    // Mengambil data jadwal berdasarkan ID
    ScheduleResponse getById(Long id);

    // Membuat jadwal baru berdasarkan permintaan dari CreateScheduleRequest
    ScheduleResponse create(CreateScheduleRequest request);

    // Memperbarui jadwal yang sudah ada berdasarkan ID dengan data dari UpdateScheduleRequest
    ScheduleResponse update(Long id, UpdateScheduleRequest request);

    // Menghapus jadwal berdasarkan ID
    void delete(Long id);

}
