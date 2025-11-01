package com.turnofacil.salta.controller;

import com.turnofacil.salta.dto.MessageResponse;
import com.turnofacil.salta.dto.admin.SpecialityRequestDTO;
import com.turnofacil.salta.dto.admin.SpecialityResponseDTO;
import com.turnofacil.salta.service.ISpecialityService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/specialities")
public class SpecialityController {
    private final ISpecialityService specialityService;

    public SpecialityController (ISpecialityService specialityService){
        this.specialityService = specialityService;
    }

    @PostMapping
    public ResponseEntity<SpecialityResponseDTO> createSpeciality(
            @Valid @RequestBody SpecialityRequestDTO requestDTO
    ) {
        SpecialityResponseDTO response = specialityService.createSpeciality(requestDTO);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<SpecialityResponseDTO>> getAllSpecialities() {
        List<SpecialityResponseDTO> responseList = specialityService.getAllSpecialities();
        return new ResponseEntity<>(responseList, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SpecialityResponseDTO> getSpecialityById(@PathVariable Integer id) {
        SpecialityResponseDTO response = specialityService.getSpecialityById(id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<SpecialityResponseDTO> updateSpeciality(
            @PathVariable Integer id,
            @Valid @RequestBody SpecialityRequestDTO requestDTO
    ) {
        SpecialityResponseDTO response = specialityService.updateSpeciality(id, requestDTO);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<MessageResponse> deleteSpeciality(@PathVariable Integer id) {
        specialityService.deleteSpeciality(id);
        return new ResponseEntity<>(new MessageResponse("Especialidad eliminada correctamente"), HttpStatus.OK);
    }
}
