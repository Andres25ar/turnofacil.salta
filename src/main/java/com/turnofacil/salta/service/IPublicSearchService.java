package com.turnofacil.salta.service;

import com.turnofacil.salta.dto.toPublic.ProfessionalResponseDTO;

//import java.time.LocalDate;
import java.time.LocalDate;
import java.util.List;

public interface IPublicSearchService {
    List<ProfessionalResponseDTO> findProfessionalByCenterAndSpeciality(Long centerId, Integer specialityId);

    List<String> findAvailableSlots (Long specialityDetailId, LocalDate date);
}
