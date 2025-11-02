package com.turnofacil.salta.service;
import com.turnofacil.salta.dto.admin.SpecialityDetailRequestDTO;
import com.turnofacil.salta.dto.admin.SpecialityDetailResponseDTO;

import java.util.List;

public interface ISpecialityDetailService {

    SpecialityDetailResponseDTO createSpecialityDetail(SpecialityDetailRequestDTO requestDTO);

    List<SpecialityDetailResponseDTO> getAllSpecialityDetails();

    SpecialityDetailResponseDTO getSpecialityDetailById(Long id);

    SpecialityDetailResponseDTO updateSpecialityDetail(Long id, SpecialityDetailRequestDTO requestDTO);

    void deleteSpecialityDetail(Long id);
}