package com.turnofacil.salta.controller;

import com.turnofacil.salta.dto.MessageResponse;
import com.turnofacil.salta.dto.appointment.AppointmentRequestDTO;
import com.turnofacil.salta.dto.appointment.AppointmentResponseDTO;
import com.turnofacil.salta.service.IAppointmentService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/appointments")
public class AppointmentController {
    private final IAppointmentService appointmentService;

    public AppointmentController(IAppointmentService appointmentService){
        this.appointmentService = appointmentService;
    }

    @PostMapping
    public ResponseEntity<AppointmentResponseDTO> createAppointment(
            @Valid @RequestBody AppointmentRequestDTO requestDTO,
            Authentication authentication
    ){
        String userEmail = authentication.getName();

        AppointmentResponseDTO responseDTO = appointmentService.createAppointment(requestDTO, userEmail);
        return new ResponseEntity<>(responseDTO, HttpStatus.CREATED);
    }

    @GetMapping("/my-appointments")
    public ResponseEntity<List<AppointmentResponseDTO>> getMyAppointments (
            Authentication authentication
    ){
        String userEmail = authentication.getName();
        List<AppointmentResponseDTO> responseList = appointmentService.getMyAppointmentsByEmail(userEmail);
        return new ResponseEntity<>(responseList, HttpStatus.OK);
    }

    @PutMapping("/cancel/{id}")
    public ResponseEntity<MessageResponse> cancelAppointment(
            @PathVariable Long id,
            Authentication authentication
    ) {
        String userEmail = authentication.getName();
        MessageResponse response = appointmentService.cancelAppointment(id, userEmail);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
