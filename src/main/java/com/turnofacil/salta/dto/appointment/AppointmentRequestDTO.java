package com.turnofacil.salta.dto.appointment;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AppointmentRequestDTO {

    // Para crear un turno, el usuario (paciente) solo necesita enviar:

    @NotNull(message = "El ID del detalle de especialidad es obligatorio")
    private Long specialityDetailId; // El ID que conecta profesional, centro y horario

    @NotNull(message = "La fecha del turno es obligatoria")
    @FutureOrPresent(message = "La fecha del turno no puede ser en el pasado")
    private LocalDate appointmentDate; // Idealmente LocalDate

    @NotNull(message = "La hora del turno es obligatoria")
    private LocalTime startTime; // Idealmente LocalTime

    // El 'userId' del paciente no lo pedimos, lo obtendremos
    // del usuario que está autenticado (logueado) en ese momento.
}

