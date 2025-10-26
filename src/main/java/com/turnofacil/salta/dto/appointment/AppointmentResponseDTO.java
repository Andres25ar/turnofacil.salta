package com.turnofacil.salta.dto.appointment;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;

//respuesta "plana" y amigable para mistrar al usuario
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AppointmentResponseDTO {

    private Long appointmentId;
    private LocalDate appointmentDate;
    private LocalTime startTime;
    private Boolean status;

    // --- Datos Aplanados ---
    // En lugar de enviar IDs, enviamos los nombres que el usuario quiere ver.
    // Llenaremos esto en la capa de Servicio.
    private String patientName;
    private String patientCuil;
    private String professionalName;
    private String professionalLicence;
    private String specialityName;
    private String healthCenterName;
    private String healthCenterAddress;
}