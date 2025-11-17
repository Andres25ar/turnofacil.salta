package com.turnofacil.salta.repository;

import com.turnofacil.salta.entity.Appointment;
import com.turnofacil.salta.entity.AppointmentStatus;
import com.turnofacil.salta.entity.SpecialityDetail;
import com.turnofacil.salta.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {

    // buscar todos los turnos de un paciente
    List<Appointment> findByUser(User user);

    boolean existsBySpecialityDetailAndAppointmentDateAndStartTimeAndStatus(
            SpecialityDetail specialityDetail,
            LocalDate appointmentDate,
            LocalTime startTime,
            AppointmentStatus status
    );

    @Query("SELECT a FROM Appointment a " +
            "JOIN FETCH a.specialityDetail sd " +
            "JOIN FETCH sd.healthCenter " +
            "JOIN FETCH sd.speciality " +
            "JOIN FETCH sd.professional p " +
            "JOIN FETCH p.user " +
            "WHERE a.user = :patient " +
            //"AND a.status = 'RESERVADO' " +
            "ORDER BY " +
            "CASE WHEN a.appointmentDate < CURRENT_DATE THEN 2 ELSE 1 END ASC, " +
            "a.appointmentDate ASC, " +
            "a.startTime ASC")
    List<Appointment> findByUserWithDetails(@Param("patient") User patient);

    @Query("SELECT a.startTime FROM Appointment a " +
            "WHERE a.specialityDetail.id = :specialityDetailId " +
            "AND a.appointmentDate = :date " +
            "AND a.status = 'RESERVADO'")
    List<LocalTime> findBookedSlotsByDetailAndDate(
            @Param("specialityDetailId") Long specialityDetailId,
            @Param("date") LocalDate date
    );

    @Query("SELECT a FROM Appointment a " +
            "JOIN FETCH a.user " +
            "JOIN FETCH a.specialityDetail sd " +
            "JOIN FETCH sd.healthCenter " +
            "JOIN FETCH sd.speciality " +
            "JOIN FETCH sd.professional p " +
            "JOIN FETCH p.user " +
            "WHERE a.specialityDetail IN :details " +
            "AND a.status = 'RESERVADO' " +
            "ORDER BY a.appointmentDate, a.startTime ASC")
    List<Appointment> findActiveBySpecialityDetailInWithDetails(
            @Param("details") List<SpecialityDetail> details
    );
}

