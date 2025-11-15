package com.turnofacil.salta.service;

import com.turnofacil.salta.dto.MessageResponse;
import com.turnofacil.salta.dto.admin.UpdateProfessionalStatusDTO;

public interface IProfessionalService {
    MessageResponse updateProfessionalStatus(Long professionalId, UpdateProfessionalStatusDTO request);
}
