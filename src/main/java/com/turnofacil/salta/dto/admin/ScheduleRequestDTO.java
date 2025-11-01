package com.turnofacil.salta.dto.admin;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ScheduleRequestDTO {

    @NotEmpty(message = "Debe proveer al menos un día de la semana (ej. 'LUNES', 'MARTES')")
    private List<String> days;

    @NotNull(message = "La hora de inicio es obligatoria")
    private LocalTime startTime;

    @NotNull(message = "La hora de finalización es obligatoria")
    private LocalTime finishTime;

    @NotNull(message = "El número de turnos es obligatorio")
    @Min(value = 1, message = "Debe haber al menos 1 turno")
    private Integer numberOfAppointment;

    @NotNull(message = "El estado (status) es obligatorio")
    private Boolean status;
}
