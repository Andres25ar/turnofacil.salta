package com.turnofacil.salta.service;

import com.turnofacil.salta.dto.admin.HealthCenterResponseDTO;
import com.turnofacil.salta.dto.admin.SpecialityResponseDTO;
import com.turnofacil.salta.dto.toPublic.ProfessionalResponseDTO;

//import java.time.LocalDate;
import java.time.LocalDate;
import java.util.List;

public interface IPublicSearchService {
    List<ProfessionalResponseDTO> findProfessionalByCenterAndSpeciality(Long centerId, Integer specialityId);

    List<String> findAvailableSlots (Long specialityDetailId, LocalDate date);

    List<HealthCenterResponseDTO> findCentersBySpeciality(Integer specialityId);

    List<SpecialityResponseDTO> findSpecialitiesByCenter(Long centerId);
}
