package com.turnofacil.salta.mapper;

import com.turnofacil.salta.dto.admin.ScheduleResponseDTO;
import com.turnofacil.salta.entity.Schedule;

public class ScheduleMapper {
    public static ScheduleResponseDTO toScheduleResponseDTO(Schedule schedule) {
        return new ScheduleResponseDTO(
                schedule.getScheduleId(),
                schedule.getDays(),
                schedule.getStartTime(),
                schedule.getFinishTime(),
                schedule.getNumberOfAppointment(),
                schedule.getStatus()
        );
    }
}
