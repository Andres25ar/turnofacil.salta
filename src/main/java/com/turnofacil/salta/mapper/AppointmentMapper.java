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

        User patient = appointment.getUser();
        SpecialityDetail detail = appointment.getSpecialityDetail();
        //User professional = appointment.getUser();
        User professional = detail.getProfessional().getUser();
        String patientFullName = patient.getLastName() + ", " + patient.getFirstName();
        String professionalFullName = professional.getLastName() + ", " + professional.getFirstName();

        return new AppointmentResponseDTO(
                appointment.getAppointmentId(),
                appointment.getAppointmentDate(),
                appointment.getStartTime(),
                appointment.getStatus(),
                patientFullName,
                patient.getCuil(),
                professionalFullName,
                detail.getProfessional().getLicence(),
                detail.getSpeciality().getName(),
                detail.getHealthCenter().getCenterName(),
                detail.getHealthCenter().getCenterAddress()
        );
    }
}
