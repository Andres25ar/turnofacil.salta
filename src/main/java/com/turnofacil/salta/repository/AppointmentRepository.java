package com.turnofacil.salta.repository;

import com.turnofacil.salta.entity.Appointment;
import com.turnofacil.salta.entity.SpecialityDetail;
import com.turnofacil.salta.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {

    // buscar todos los turnos de un paciente
    List<Appointment> findByUser(User user);

    boolean existsBySpecialityDetailAndAppointmentDateAndStartTime(
            SpecialityDetail specialityDetail,
            LocalDate appointmentDate,
            LocalTime startTime
    );

    @Query("SELECT a FROM Appointment a " +
            "JOIN FETCH a.specialityDetail sd " +
            "JOIN FETCH sd.healthCenter " +
            "JOIN FETCH sd.speciality " +
            "JOIN FETCH sd.professional p " +
            "JOIN FETCH p.user " +
            "WHERE a.user = :patient")
    List<Appointment> findByUserWithDetails(@Param("patient") User patient);
}

