package com.turnofacil.salta.service;

import com.turnofacil.salta.dto.admin.ScheduleRequestDTO;
import com.turnofacil.salta.dto.admin.ScheduleResponseDTO;

import java.util.List;

public interface IScheduleService {
    ScheduleResponseDTO createSchedule(ScheduleRequestDTO requestDTO);

    List<ScheduleResponseDTO> getAllSchedules();

    ScheduleResponseDTO getScheduleById(Long id);

    ScheduleResponseDTO updateSchedule(Long id, ScheduleRequestDTO requestDTO);

    void deleteSchedule(Long id);
}
