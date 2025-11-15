package com.turnofacil.salta.service;

import com.turnofacil.salta.dto.admin.TypeOfCenterResponseDTO;

import java.util.List;

public interface ITypeOfCenterService {
    List<TypeOfCenterResponseDTO> getAllTypes();
}
