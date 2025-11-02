package com.turnofacil.salta.dto.admin;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SpecialityDetailRequestDTO {
    @NotNull(message = "El ID del centro de salud es obligatorio")
    private Long healthCenterId;

    @NotNull(message = "El ID de la especialidad es obligatorio")
    private Integer specialityId;

    @NotNull(message = "El ID del profesional es obligatorio")
    private Long professionalId;

    @NotNull(message = "El ID del horario (schedule) es obligatorio")
    private Long scheduleId;
}
