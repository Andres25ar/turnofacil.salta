package com.turnofacil.salta.controller;

import com.turnofacil.salta.dto.toPublic.ProfessionalResponseDTO;
import com.turnofacil.salta.service.IPublicSearchService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/v1/public")
public class PublicSearchController {
    private final IPublicSearchService publicSearchService;

    public PublicSearchController(IPublicSearchService publicSearchService) {
        this.publicSearchService = publicSearchService;
    }

    @GetMapping("/professionals-by-filter")
    public ResponseEntity<List<ProfessionalResponseDTO>> getProfessionals(
            @RequestParam Long centerId,
            @RequestParam Integer specialityId
    ){
        List<ProfessionalResponseDTO> professionals = publicSearchService
                .findProfessionalByCenterAndSpeciality(centerId, specialityId);

        return ResponseEntity.ok(professionals);
    }

    //endpoint para los horarios
    @GetMapping("/availability")
    public ResponseEntity<List<String>> getAvailability(
            @RequestParam Long specialityDetailId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)LocalDate date
    ){
        List<String> slots = publicSearchService.findAvailableSlots(specialityDetailId, date);
        return ResponseEntity.ok(slots);
    }

}
