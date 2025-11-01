package com.turnofacil.salta.controller;

import com.turnofacil.salta.dto.MessageResponse;
import com.turnofacil.salta.dto.admin.HealthCenterRequestDTO;
import com.turnofacil.salta.dto.admin.HealthCenterResponseDTO;
import com.turnofacil.salta.service.IHealthCenterService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/health-centers")
public class HealthCenterController {
    private final IHealthCenterService healthCenterService;

    public HealthCenterController(IHealthCenterService healthCenterService){
        this.healthCenterService = healthCenterService;
    }

    @PostMapping
    public ResponseEntity<HealthCenterResponseDTO> createHealthCenter(
        @Valid @RequestBody HealthCenterRequestDTO requestDTO   //valida que es una request valida
    ){
        //usa los service para crear una response por medio de mapping
        HealthCenterResponseDTO responseDTO = healthCenterService.createHealthCenter(requestDTO);
        //retorna una 201 CREATED de http... POST exitoso
        return new ResponseEntity<>(responseDTO, HttpStatus.CREATED);
    }

    /*retorna todos los centros */
    @GetMapping
    public ResponseEntity<List<HealthCenterResponseDTO>> getAllHealthCenters(){
        //guarda todos los centros del get de service en una lista
        List<HealthCenterResponseDTO> responseList = healthCenterService.getAllHealthCenters();
        //retorna 200 OK de http... GET exitoso
        return new ResponseEntity<>(responseList, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<HealthCenterResponseDTO> getHealthCenterById (
        @PathVariable Long id   //toma el valor del mapping "/{id}" y lo guarda en la variable "id"
    ){
        //usa en get por id de service
        HealthCenterResponseDTO responseDTO = healthCenterService.getHealthCenterById(id);
        //retorna 200 OK de http... GET exitoso
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<HealthCenterResponseDTO> updateHealthCenterById (
        @PathVariable Long id,
        @Valid @RequestBody HealthCenterRequestDTO requestDTO
    ){
        //usa el metodo update por id del service
        HealthCenterResponseDTO responseDTO = healthCenterService.updateHealthCenterById(id, requestDTO);
        //retorna 200 OK de http... UPDATE exitoso
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<MessageResponse> deleteHealthCenterById (
        @PathVariable Long id
    ){
        healthCenterService.deleteHealthCenterById(id);
        return new ResponseEntity<>(new MessageResponse("Centro de salud eliminado correctamente"), HttpStatus.OK);
    }
}













