package com.turnofacil.salta.mapper;

import com.turnofacil.salta.dto.toPublic.ProfessionalResponseDTO;
import com.turnofacil.salta.entity.SpecialityDetail;

public class ProfessionalMapper {
    public static ProfessionalResponseDTO professionalResponseDTO(SpecialityDetail detail) {
        if (detail == null || detail.getProfessional() == null){
            return null;
        }

        String fullName = detail.getProfessional().getUser().getFirstName() + " " + detail.getProfessional().getUser().getLastName();

        return new ProfessionalResponseDTO(
                detail.getProfessional().getProfesionalId(),
                fullName,
                detail.getProfessional().getLicence(),
                detail.getSpecialityDetailId()
        );
    }
}
