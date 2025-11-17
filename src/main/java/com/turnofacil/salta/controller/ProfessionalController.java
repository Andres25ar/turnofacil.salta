package com.turnofacil.salta.controller;

import com.turnofacil.salta.dto.MessageResponse;
import com.turnofacil.salta.dto.admin.UpdateProfessionalStatusDTO;
import com.turnofacil.salta.dto.appointment.AppointmentResponseDTO;
import com.turnofacil.salta.service.IProfessionalService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/professionals")
//@PreAuthorize("hasRole('ADMIN')") // Protegido solo para Admins
@PreAuthorize("hasAnyRole('ADMIN', 'PROFESIONAL')")
public class ProfessionalController {

    private final IProfessionalService professionalService;

    public ProfessionalController(IProfessionalService professionalService) {
        this.professionalService = professionalService;
    }

    /**
     * Endpoint para cambiar el estado de un profesional
     * (Ej. ACTIVO, DE_VACACIONES, SUSPENDIDO)
     * El ID aquí es el professional_id, NO el user_id.
     */
    @PutMapping("/{id}/status")
    public ResponseEntity<MessageResponse> updateStatus(
            @PathVariable Long id,
            @Valid @RequestBody UpdateProfessionalStatusDTO request
    ) {
        MessageResponse response = professionalService.updateProfessionalStatus(id, request);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/my-agenda")
    @PreAuthorize("hasRole('PROFESIONAL')")
    public ResponseEntity<List<AppointmentResponseDTO>> getMyAgenda(Authentication authentication) {
        String userEmail = authentication.getName();
        List<AppointmentResponseDTO> ageda = professionalService.getMyAgenda(userEmail);
        return ResponseEntity.ok(ageda);
    }

    @PutMapping("/appointments/{id}/cancel")
    @PreAuthorize("hasRole('PROFESIONAL')")
    public ResponseEntity<MessageResponse> cancelAppointment(
            @PathVariable Long id,
            Authentication authentication) {
        String email = authentication.getName();
        MessageResponse response = professionalService.professionalCancelAppointment(id, email);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/appointments/{id}/complete")
    @PreAuthorize("hasRole('PROFESIONAL')")
    public ResponseEntity<MessageResponse> completeAppointment(
            @PathVariable Long id,
            Authentication authentication) {
        String email = authentication.getName();
        MessageResponse response = professionalService.completeAppointment(id, email);
        return ResponseEntity.ok(response);
    }
}