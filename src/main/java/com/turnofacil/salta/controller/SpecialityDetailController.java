package com.turnofacil.salta.controller;

import com.turnofacil.salta.dto.MessageResponse;
import com.turnofacil.salta.dto.admin.SpecialityDetailRequestDTO;
import com.turnofacil.salta.dto.admin.SpecialityDetailResponseDTO;
import com.turnofacil.salta.service.ISpecialityDetailService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/speciality-details")
public class SpecialityDetailController {

    private final ISpecialityDetailService specialityDetailService;

    public SpecialityDetailController(ISpecialityDetailService specialityDetailService) {
        this.specialityDetailService = specialityDetailService;
    }

    @PostMapping
    public ResponseEntity<SpecialityDetailResponseDTO> createSpecialityDetail(
            @Valid @RequestBody SpecialityDetailRequestDTO requestDTO
    ) {
        SpecialityDetailResponseDTO response = specialityDetailService.createSpecialityDetail(requestDTO);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<SpecialityDetailResponseDTO>> getAllSpecialityDetails() {
        List<SpecialityDetailResponseDTO> responseList = specialityDetailService.getAllSpecialityDetails();
        return new ResponseEntity<>(responseList, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SpecialityDetailResponseDTO> getSpecialityDetailById(@PathVariable Long id) {
        SpecialityDetailResponseDTO response = specialityDetailService.getSpecialityDetailById(id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<SpecialityDetailResponseDTO> updateSpecialityDetail(
            @PathVariable Long id,
            @Valid @RequestBody SpecialityDetailRequestDTO requestDTO
    ) {
        SpecialityDetailResponseDTO response = specialityDetailService.updateSpecialityDetail(id, requestDTO);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<MessageResponse> deleteSpecialityDetail(@PathVariable Long id) {
        specialityDetailService.deleteSpecialityDetail(id);
        return new ResponseEntity<>(new MessageResponse("Asignación (SpecialityDetail) eliminada correctamente"), HttpStatus.OK);
    }
}

