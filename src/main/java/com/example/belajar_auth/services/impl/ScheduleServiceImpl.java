package com.example.belajar_auth.services.impl;

import com.example.belajar_auth.dto.request.CreateScheduleRequest;
import com.example.belajar_auth.dto.request.UpdateScheduleRequest;
import com.example.belajar_auth.dto.response.ScheduleResponse;
import com.example.belajar_auth.entities.Schedule;
import com.example.belajar_auth.repositories.ScheduleRepository;
import com.example.belajar_auth.services.ScheduleService;
import com.example.belajar_auth.services.ValidationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service // Menandakan bahwa kelas ini adalah Service dalam Spring
public class ScheduleServiceImpl implements ScheduleService {

    @Autowired
    private ScheduleRepository scheduleRepository; // Repository untuk mengakses database

    @Autowired
    private ValidationService validationService; // Service untuk validasi data

    @Override
    public List<ScheduleResponse> list() {
        // Mengambil semua data jadwal dari database
        List<Schedule> schedules = scheduleRepository.findAll();
        // Mengonversi entitas Schedule menjadi ScheduleResponse
        return schedules.stream().map(this::toScheduleResponse).toList();
    }

    @Override
    public ScheduleResponse getById(Long id) {
        // Mencari jadwal berdasarkan ID, jika tidak ditemukan, lempar exception
        Schedule schedule = scheduleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Schedule not found"));
        // Mengonversi entitas Schedule ke ScheduleResponse
        return toScheduleResponse(schedule);
    }

    @Override
    @Transactional // Menandakan bahwa metode ini akan dijalankan dalam transaksi
    public ScheduleResponse create(CreateScheduleRequest request) {

        System.out.println(request.getDestination()); // Mencetak tujuan keberangkatan ke konsol

        validationService.validate(request); // Validasi input pengguna

        // Membuat objek Schedule baru berdasarkan request
        Schedule schedule = Schedule.builder()
                .destination(request.getDestination()) // Tujuan keberangkatan
                .departureDate(request.getDepartureDate()) // Tanggal keberangkatan
                .departureTime(request.getDepartureTime()) // Waktu keberangkatan
                .quota(request.getQuota()) // Kuota penumpang
                .price(request.getPrice()) // Harga tiket
                .build();

        schedule = scheduleRepository.save(schedule); // Menyimpan data ke database
        return toScheduleResponse(schedule); // Mengonversi ke ScheduleResponse dan mengembalikan hasilnya
    }

    @Override
    @Transactional
    public ScheduleResponse update(Long id, UpdateScheduleRequest request) {
        validationService.validate(request); // Validasi input pengguna

        // Mencari data jadwal berdasarkan ID, jika tidak ditemukan, lempar exception
        Schedule schedule = scheduleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Schedule not found"));

        // Memperbarui data jadwal dengan nilai baru dari request
        schedule.setDestination(request.getDestination());
        schedule.setDepartureDate(request.getDepartureDate());
        schedule.setDepartureTime(request.getDepartureTime());
        schedule.setQuota(request.getQuota());
        schedule.setPrice(request.getPrice());

        schedule = scheduleRepository.save(schedule); // Menyimpan perubahan ke database
        return toScheduleResponse(schedule); // Mengembalikan data yang telah diperbarui
    }

    @Override
    @Transactional
    public void delete(Long id) {
        // Memeriksa apakah data jadwal dengan ID tertentu ada di database
        if (!scheduleRepository.existsById(id)) {
            throw new RuntimeException("Schedule not found");
        }
        scheduleRepository.deleteById(id); // Menghapus jadwal berdasarkan ID
    }

    // Metode untuk mengonversi entitas Schedule menjadi ScheduleResponse
    private ScheduleResponse toScheduleResponse(Schedule schedule) {
        return ScheduleResponse.builder()
                .id(schedule.getId())
                .destination(schedule.getDestination())
                .departureDate(schedule.getDepartureDate())
                .departureTime(schedule.getDepartureTime())
                .quota(schedule.getQuota())
                .price(schedule.getPrice())
                .createdAt(schedule.getCreatedAt()) // Tanggal pembuatan jadwal
                .updatedAt(schedule.getUpdatedAt()) // Tanggal terakhir diperbarui
                .build();
    }
}
