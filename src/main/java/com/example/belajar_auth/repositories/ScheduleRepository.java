package com.example.belajar_auth.repositories;

import com.example.belajar_auth.entities.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ScheduleRepository extends JpaRepository<Schedule, Long> {
}
