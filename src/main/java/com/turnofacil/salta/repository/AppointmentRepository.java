package com.turnofacil.salta.repository;

import com.turnofacil.salta.entity.Appointment;
import com.turnofacil.salta.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;
import java.util.List;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {

    // Nota: Usamos BigInteger porque así lo definiste en tu entidad Appointment.

    // Para buscar todos los turnos de un paciente
    List<Appointment> findByUser(User user);

    // Más adelante añadiremos búsquedas por fecha y profesional
}

