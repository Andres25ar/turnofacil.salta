package com.turnofacil.salta.repository;

import com.turnofacil.salta.entity.Appointment;
import com.turnofacil.salta.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;
import java.util.List;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {

    // buscar todos los turnos de un paciente
    List<Appointment> findByUser(User user);

    // mas adelante añadir búsquedas por fecha y profesional
}

