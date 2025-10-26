package com.turnofacil.salta.repository;

import com.turnofacil.salta.entity.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ScheduleRepository extends JpaRepository<Schedule, Long> {
    // Por ahora, los métodos CRUD básicos son suficientes.
    // Más adelante podríamos añadir consultas por rangos de horas, etc.
}
