package com.turnofacil.salta.controller;

import com.turnofacil.salta.dto.MessageResponse;
import com.turnofacil.salta.dto.admin.UpdateProfessionalStatusDTO;
import com.turnofacil.salta.service.IProfessionalService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/professionals")
@PreAuthorize("hasRole('ADMIN')") // Protegido solo para Admins
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

    // Aquí podrías añadir un GET /api/v1/professionals para listarlos
}