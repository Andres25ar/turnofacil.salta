package com.turnofacil.salta.mapper;

import com.turnofacil.salta.dto.admin.TypeOfCenterResponseDTO;
import com.turnofacil.salta.entity.TypeOfCenter;

public class TypeOfCenterMapper {
    public static TypeOfCenterResponseDTO toResponseDTO(TypeOfCenter entity) {
        if (entity == null){
            return null;
        }
        return new TypeOfCenterResponseDTO(
                entity.getId(),
                entity.getType()
        );
    }
}
