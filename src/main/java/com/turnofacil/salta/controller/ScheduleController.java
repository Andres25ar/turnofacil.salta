package com.turnofacil.salta.controller;

import com.turnofacil.salta.dto.MessageResponse;
import com.turnofacil.salta.dto.admin.ScheduleRequestDTO;
import com.turnofacil.salta.dto.admin.ScheduleResponseDTO;
import com.turnofacil.salta.service.IScheduleService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/schedules")
public class ScheduleController {
    private final IScheduleService scheduleService;

    public ScheduleController(IScheduleService scheduleService) {
        this.scheduleService = scheduleService;
    }

    @PostMapping
    public ResponseEntity<ScheduleResponseDTO> createSchedule(
            @Valid @RequestBody ScheduleRequestDTO requestDTO
    ) {
        ScheduleResponseDTO response = scheduleService.createSchedule(requestDTO);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<ScheduleResponseDTO>> getAllSchedules() {
        List<ScheduleResponseDTO> responseList = scheduleService.getAllSchedules();
        return new ResponseEntity<>(responseList, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ScheduleResponseDTO> getScheduleById(@PathVariable Long id) {
        ScheduleResponseDTO response = scheduleService.getScheduleById(id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ScheduleResponseDTO> updateSchedule(
            @PathVariable Long id,
            @Valid @RequestBody ScheduleRequestDTO requestDTO
    ) {
        ScheduleResponseDTO response = scheduleService.updateSchedule(id, requestDTO);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<MessageResponse> deleteSchedule(@PathVariable Long id) {
        scheduleService.deleteSchedule(id);
        return new ResponseEntity<>(new MessageResponse("Horario (Schedule) eliminado correctamente"), HttpStatus.OK);
    }
}
