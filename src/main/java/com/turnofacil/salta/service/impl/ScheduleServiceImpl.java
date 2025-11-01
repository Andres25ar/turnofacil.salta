package com.turnofacil.salta.service.impl;

import com.turnofacil.salta.dto.admin.ScheduleRequestDTO;
import com.turnofacil.salta.dto.admin.ScheduleResponseDTO;
import com.turnofacil.salta.entity.Schedule;
import com.turnofacil.salta.exception.ResourceNotFoundException;
import com.turnofacil.salta.mapper.ScheduleMapper;
import com.turnofacil.salta.repository.ScheduleRepository;
import com.turnofacil.salta.service.IScheduleService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ScheduleServiceImpl implements IScheduleService {
    private final ScheduleRepository scheduleRepository;

    public ScheduleServiceImpl(ScheduleRepository scheduleRepository) {
        this.scheduleRepository = scheduleRepository;
    }

    @Override
    @Transactional
    public ScheduleResponseDTO createSchedule(ScheduleRequestDTO requestDTO) {
        Schedule schedule = new Schedule();
        schedule.setDays(requestDTO.getDays());
        schedule.setStartTime(requestDTO.getStartTime());
        schedule.setFinishTime(requestDTO.getFinishTime());
        schedule.setNumberOfAppointment(requestDTO.getNumberOfAppointment());
        schedule.setStatus(requestDTO.getStatus());

        Schedule savedSchedule = scheduleRepository.save(schedule);

        return ScheduleMapper.toScheduleResponseDTO(savedSchedule);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ScheduleResponseDTO> getAllSchedules() {
        List<Schedule> schedules = scheduleRepository.findAll();
        return schedules.stream()
                .map(ScheduleMapper::toScheduleResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public ScheduleResponseDTO getScheduleById(Long id) {
        Schedule schedule = scheduleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Schedule", "id", id));
        return ScheduleMapper.toScheduleResponseDTO(schedule);
    }

    @Override
    @Transactional
    public ScheduleResponseDTO updateSchedule(Long id, ScheduleRequestDTO requestDTO) {
        Schedule schedule = scheduleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Schedule", "id", id));

        schedule.setDays(requestDTO.getDays());
        schedule.setStartTime(requestDTO.getStartTime());
        schedule.setFinishTime(requestDTO.getFinishTime());
        schedule.setNumberOfAppointment(requestDTO.getNumberOfAppointment());
        schedule.setStatus(requestDTO.getStatus());

        Schedule updatedSchedule = scheduleRepository.save(schedule);
        return ScheduleMapper.toScheduleResponseDTO(updatedSchedule);
    }

    @Override
    @Transactional
    public void deleteSchedule(Long id) {
        Schedule schedule = scheduleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Schedule", "id", id));

        // Faltaría validar que no esté en uso por un SpecialityDetail

        scheduleRepository.delete(schedule);
    }
}
