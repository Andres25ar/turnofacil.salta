package com.turnofacil.salta.mapper;

import com.turnofacil.salta.dto.admin.SpecialityResponseDTO;
import com.turnofacil.salta.entity.Speciality;

public class SpecialityMapper {
    public static SpecialityResponseDTO toSpecialityResponseDTO(Speciality speciality){
        return new SpecialityResponseDTO(
                speciality.getSpecialityId(),
                speciality.getName(),
                speciality.getDescription()
        );
    }
}
