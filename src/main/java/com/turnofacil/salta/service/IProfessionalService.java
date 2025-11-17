package com.turnofacil.salta.service;

import com.turnofacil.salta.dto.MessageResponse;
import com.turnofacil.salta.dto.admin.UpdateProfessionalStatusDTO;
import com.turnofacil.salta.dto.appointment.AppointmentResponseDTO;

import java.util.List;

public interface IProfessionalService {
    MessageResponse updateProfessionalStatus(Long professionalId, UpdateProfessionalStatusDTO request);
    List<AppointmentResponseDTO> getMyAgenda (String userEmail);
    MessageResponse professionalCancelAppointment(Long appointmentId, String professionalEmail);
    MessageResponse completeAppointment(Long appointmentId, String professionalEmail);
}
