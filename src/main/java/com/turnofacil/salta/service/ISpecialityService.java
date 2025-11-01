package com.turnofacil.salta.service;

import com.turnofacil.salta.dto.admin.SpecialityRequestDTO;
import com.turnofacil.salta.dto.admin.SpecialityResponseDTO;

import java.util.List;

public interface ISpecialityService {
    SpecialityResponseDTO createSpeciality(SpecialityRequestDTO requestDTO);

    List<SpecialityResponseDTO> getAllSpecialities();

    SpecialityResponseDTO getSpecialityById(Integer id);

    SpecialityResponseDTO updateSpeciality(Integer id, SpecialityRequestDTO requestDTO);

    void deleteSpeciality(Integer id);
}
