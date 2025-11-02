package com.turnofacil.salta.dto.admin;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SpecialityDetailResponseDTO {

    // El ID del enlace en sí
    private Long specialityDetailId;

    // Info del Centro
    private String healthCenterName;
    private String healthCenterAddress;

    // Info del Profesional
    private String professionalFullName;
    private String professionalLicence;

    // Info de la Especialidad
    private String specialityName;

    // Info del Horario
    private String scheduleDescription;
}
