package com.turnofacil.salta.service.impl;

import com.turnofacil.salta.dto.MessageResponse;
import com.turnofacil.salta.dto.admin.UpdateProfessionalStatusDTO;
import com.turnofacil.salta.entity.Professional;
import com.turnofacil.salta.entity.ProfessionalStatus;
import com.turnofacil.salta.entity.SpecialityDetail;
import com.turnofacil.salta.exception.ResourceNotFoundException;
import com.turnofacil.salta.repository.ProfessionalRepository;
import com.turnofacil.salta.repository.SpecialityDetailRepository;
import com.turnofacil.salta.service.IProfessionalService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ProfessionalServiceImpl implements IProfessionalService {

    private final ProfessionalRepository professionalRepository;
    private final SpecialityDetailRepository specialityDetailRepository;

    public ProfessionalServiceImpl(ProfessionalRepository professionalRepository,
                                   SpecialityDetailRepository specialityDetailRepository) {
        this.professionalRepository = professionalRepository;
        this.specialityDetailRepository = specialityDetailRepository;
    }

    @Override
    @Transactional
    public MessageResponse updateProfessionalStatus(Long professionalId, UpdateProfessionalStatusDTO request) {
        // 1. Encontrar al profesional
        Professional professional = professionalRepository.findById(professionalId)
                .orElseThrow(() -> new ResourceNotFoundException("Professional", "id", professionalId));

        // 2. Actualizar su estado
        professional.setStatus(request.getStatus());
        professionalRepository.save(professional);

        // 3. Determinar si sus horarios deben estar activos
        // Solo están activos si el profesional está ACTIVO.
        boolean areSchedulesActive = request.getStatus() == ProfessionalStatus.ACTIVO;

        // 4. Encontrar TODAS sus asignaciones (SpecialityDetail)
        List<SpecialityDetail> details = specialityDetailRepository.findByProfessional(professional);

        // 5. Propagar el cambio de estado a todas sus asignaciones
        for (SpecialityDetail detail : details) {
            detail.setStatus(areSchedulesActive);
            specialityDetailRepository.save(detail);
        }

        return new MessageResponse(
                "Estado del profesional actualizado a " + request.getStatus() +
                        ". " + details.size() + " asignaciones de horarios han sido " +
                        (areSchedulesActive ? "activadas." : "desactivadas.")
        );
    }
}