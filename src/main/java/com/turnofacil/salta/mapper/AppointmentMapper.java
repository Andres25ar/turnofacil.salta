package com.turnofacil.salta.mapper;

import com.turnofacil.salta.dto.appointment.AppointmentResponseDTO;
import com.turnofacil.salta.entity.Appointment;
import com.turnofacil.salta.entity.SpecialityDetail;
import com.turnofacil.salta.entity.User;

public class AppointmentMapper {
    public static AppointmentResponseDTO toResponseDTO (Appointment appointment){
        if(appointment == null){
            return null;
        }

        // --- 1. Datos del Paciente (Defensivo) ---
        User patient = appointment.getUser();
        String patientFullName = (patient != null) ? patient.getLastName() + ", " + patient.getFirstName() : "Paciente no asignado";
        String patientCuil = (patient != null) ? patient.getCuil() : "N/A";

        // --- 2. Datos del Detalle (Defensivo) ---
        SpecialityDetail detail = appointment.getSpecialityDetail();

        // Valores por defecto que usaremos si algo es nulo
        String professionalFullName = "Profesional no asignado";
        String professionalLicence = "N/A";
        String specialityName = "Especialidad no disponible";
        String healthCenterName = "Centro no disponible";
        String healthCenterAddress = "N/A";

        // --- 3. Validación de Nulos ---
        // Solo si el 'detail' (el enlace) existe, intentamos leer sus datos
        if (detail != null) {

            // Validar Profesional
            if (detail.getProfessional() != null && detail.getProfessional().getUser() != null) {
                User professionalUser = detail.getProfessional().getUser();
                professionalFullName = professionalUser.getLastName() + ", " + professionalUser.getFirstName();
                professionalLicence = detail.getProfessional().getLicence();
            }

            // Validar Especialidad
            if (detail.getSpeciality() != null) {
                specialityName = detail.getSpeciality().getName();
            }

            // Validar Centro de Salud
            if (detail.getHealthCenter() != null) {
                healthCenterName = detail.getHealthCenter().getCenterName();
                healthCenterAddress = detail.getHealthCenter().getCenterAddress();
            }
        }

        // --- 4. Retorno Seguro ---
        // Ahora devolvemos el DTO con los datos seguros (o los valores por defecto)
        return new AppointmentResponseDTO(
                appointment.getAppointmentId(),
                appointment.getAppointmentDate(),
                appointment.getStartTime(),
                appointment.getStatus().toString(),
                patientFullName,
                patientCuil,
                professionalFullName,
                professionalLicence,
                specialityName,
                healthCenterName,
                healthCenterAddress
        );
    }
}