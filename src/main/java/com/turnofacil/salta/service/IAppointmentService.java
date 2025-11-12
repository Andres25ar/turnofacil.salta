package com.turnofacil.salta.service;

import com.turnofacil.salta.dto.MessageResponse;
import com.turnofacil.salta.dto.appointment.AppointmentRequestDTO;
import com.turnofacil.salta.dto.appointment.AppointmentResponseDTO;

import java.util.List;

public interface IAppointmentService {
    AppointmentResponseDTO createAppointment (AppointmentRequestDTO requestDTO, String userEmail);

    List<AppointmentResponseDTO> getMyAppointmentsByEmail (String userEmail);

    List<AppointmentResponseDTO> getMyAppointmentsByCuil (String cuil);

    MessageResponse cancelAppointment (Long appointmentId, String userEmail);
}
