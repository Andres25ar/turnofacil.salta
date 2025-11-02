package com.turnofacil.salta.service.impl;

import com.turnofacil.salta.dto.admin.SpecialityDetailRequestDTO;
import com.turnofacil.salta.dto.admin.SpecialityDetailResponseDTO;
import com.turnofacil.salta.entity.*; // Importar todas las entidades
import com.turnofacil.salta.exception.ResourceNotFoundException;
import com.turnofacil.salta.mapper.SpecialityDetailMapper;
import com.turnofacil.salta.repository.*; // Importar todos los repos
import com.turnofacil.salta.service.ISpecialityDetailService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SpecialityDetailServiceImpl implements ISpecialityDetailService {

    // ¡Inyectamos los 5 repositorios que necesitamos!
    private final SpecialityDetailRepository specialityDetailRepository;
    private final HealthCenterRepository healthCenterRepository;
    private final SpecialityRepository specialityRepository;
    private final ProfessionalRepository professionalRepository;
    private final ScheduleRepository scheduleRepository;

    public SpecialityDetailServiceImpl(SpecialityDetailRepository specialityDetailRepository,
                                       HealthCenterRepository healthCenterRepository,
                                       SpecialityRepository specialityRepository,
                                       ProfessionalRepository professionalRepository,
                                       ScheduleRepository scheduleRepository) {
        this.specialityDetailRepository = specialityDetailRepository;
        this.healthCenterRepository = healthCenterRepository;
        this.specialityRepository = specialityRepository;
        this.professionalRepository = professionalRepository;
        this.scheduleRepository = scheduleRepository;
    }

    @Override
    @Transactional
    public SpecialityDetailResponseDTO createSpecialityDetail(SpecialityDetailRequestDTO requestDTO) {
        // 1. Buscar todas las entidades por sus IDs
        HealthCenter center = healthCenterRepository.findById(requestDTO.getHealthCenterId())
                .orElseThrow(() -> new ResourceNotFoundException("HealthCenter", "id", requestDTO.getHealthCenterId()));

        Speciality speciality = specialityRepository.findById(requestDTO.getSpecialityId())
                .orElseThrow(() -> new ResourceNotFoundException("Speciality", "id", requestDTO.getSpecialityId()));

        Professional professional = professionalRepository.findById(requestDTO.getProfessionalId())
                .orElseThrow(() -> new ResourceNotFoundException("Professional", "id", requestDTO.getProfessionalId()));

        Schedule schedule = scheduleRepository.findById(requestDTO.getScheduleId())
                .orElseThrow(() -> new ResourceNotFoundException("Schedule", "id", requestDTO.getScheduleId()));

        // 2. Validar que esta combinación no exista ya
        // (Un profesional no puede tener la misma especialidad en el mismo centro,
        // pero sí podría tenerla con otro horario. Por ahora, validamos una combinación más simple)
        specialityDetailRepository.findByHealthCenterAndSpecialityAndProfessional(center, speciality, professional)
                .ifPresent(detail -> {
                    throw new IllegalArgumentException("Este profesional ya está asignado a esta especialidad en este centro.");
                });

        // 3. Crear la nueva entidad de enlace
        SpecialityDetail newDetail = new SpecialityDetail();
        newDetail.setHealthCenter(center);
        newDetail.setSpeciality(speciality);
        newDetail.setProfessional(professional);
        newDetail.setSchedule(schedule);

        SpecialityDetail savedDetail = specialityDetailRepository.save(newDetail);

        // 4. Mapear y devolver la respuesta aplanada
        return SpecialityDetailMapper.toResponseDTO(savedDetail);
    }

    @Override
    @Transactional(readOnly = true)
    public List<SpecialityDetailResponseDTO> getAllSpecialityDetails() {
        // Usamos nuestra consulta optimizada
        List<SpecialityDetail> details = specialityDetailRepository.findAllWithDetails();
        return details.stream()
                .map(SpecialityDetailMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public SpecialityDetailResponseDTO getSpecialityDetailById(Long id) {
        SpecialityDetail detail = specialityDetailRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("SpecialityDetail", "id", id));
        return SpecialityDetailMapper.toResponseDTO(detail);
    }

    @Override
    @Transactional
    public SpecialityDetailResponseDTO updateSpecialityDetail(Long id, SpecialityDetailRequestDTO requestDTO) {
        // 1. Encontrar el enlace existente
        SpecialityDetail detailToUpdate = specialityDetailRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("SpecialityDetail", "id", id));

        // 2. Encontrar las *nuevas* entidades
        HealthCenter center = healthCenterRepository.findById(requestDTO.getHealthCenterId())
                .orElseThrow(() -> new ResourceNotFoundException("HealthCenter", "id", requestDTO.getHealthCenterId()));

        Speciality speciality = specialityRepository.findById(requestDTO.getSpecialityId())
                .orElseThrow(() -> new ResourceNotFoundException("Speciality", "id", requestDTO.getSpecialityId()));

        Professional professional = professionalRepository.findById(requestDTO.getProfessionalId())
                .orElseThrow(() -> new ResourceNotFoundException("Professional", "id", requestDTO.getProfessionalId()));

        Schedule schedule = scheduleRepository.findById(requestDTO.getScheduleId())
                .orElseThrow(() -> new ResourceNotFoundException("Schedule", "id", requestDTO.getScheduleId()));

        // 3. Actualizar el enlace
        detailToUpdate.setHealthCenter(center);
        detailToUpdate.setSpeciality(speciality);
        detailToUpdate.setProfessional(professional);
        detailToUpdate.setSchedule(schedule);

        SpecialityDetail updatedDetail = specialityDetailRepository.save(detailToUpdate);

        return SpecialityDetailMapper.toResponseDTO(updatedDetail);
    }

    @Override
    @Transactional
    public void deleteSpecialityDetail(Long id) {
        SpecialityDetail detail = specialityDetailRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("SpecialityDetail", "id", id));

        // Validar que no tenga turnos (Appointments) asociados
        // if (appointmentRepository.existsBySpecialityDetail(detail)) {
        //     throw new IllegalArgumentException("No se puede eliminar: Este detalle tiene turnos asociados.");
        // }

        specialityDetailRepository.delete(detail);
    }
}
