package com.turnofacil.salta.dto.admin;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalTime;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ScheduleResponseDTO {
    private Long scheduleId;
    private List<String> days;
    private LocalTime startTime;
    private LocalTime finishTime;
    private Integer numberOfAppointment;
    private Boolean status;
}
