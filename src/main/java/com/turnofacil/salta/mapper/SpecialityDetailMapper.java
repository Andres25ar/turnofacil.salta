package com.turnofacil.salta.mapper;

import com.turnofacil.salta.dto.admin.SpecialityDetailResponseDTO;
import com.turnofacil.salta.entity.SpecialityDetail;

import java.time.format.DateTimeFormatter;

public class SpecialityDetailMapper {
    public static SpecialityDetailResponseDTO toResponseDTO(SpecialityDetail detail) {
        if (detail == null) {
            return null;
        }

        // Construir el nombre completo del profesional
        String fullName = detail.getProfessional().getUser().getFirstName() +
                " " + detail.getProfessional().getUser().getLastName();

        // Construir la descripción del horario
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
        String scheduleDesc = String.join(", ", detail.getSchedule().getDays()) +
                ": " + detail.getSchedule().getStartTime().format(timeFormatter) +
                " - " + detail.getSchedule().getFinishTime().format(timeFormatter);

        return new SpecialityDetailResponseDTO(
                detail.getSpecialityDetailId(),
                detail.getHealthCenter().getCenterName(),
                detail.getHealthCenter().getCenterAddress(),
                fullName,
                detail.getProfessional().getLicence(),
                detail.getSpeciality().getName(),
                scheduleDesc
        );
    }
}
