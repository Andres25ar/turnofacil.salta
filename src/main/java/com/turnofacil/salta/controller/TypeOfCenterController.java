package com.turnofacil.salta.controller;

import com.turnofacil.salta.dto.admin.TypeOfCenterResponseDTO;
import com.turnofacil.salta.service.ITypeOfCenterService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/type-of-center")
public class TypeOfCenterController {
    private final ITypeOfCenterService typeOfCenterService;

    public TypeOfCenterController(ITypeOfCenterService typeOfCenterService) {
        this.typeOfCenterService = typeOfCenterService;
    }

    @GetMapping
    public ResponseEntity<List<TypeOfCenterResponseDTO>> getAllTypes(){
        List<TypeOfCenterResponseDTO> types = typeOfCenterService.getAllTypes();

        return ResponseEntity.ok(types);
    }
}
