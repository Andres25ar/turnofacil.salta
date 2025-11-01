package com.turnofacil.salta.service.impl;

import com.turnofacil.salta.dto.admin.SpecialityRequestDTO;
import com.turnofacil.salta.dto.admin.SpecialityResponseDTO;
import com.turnofacil.salta.entity.Speciality;
import com.turnofacil.salta.exception.ResourceNotFoundException;
import com.turnofacil.salta.mapper.SpecialityMapper;
import com.turnofacil.salta.repository.SpecialityRepository;
import com.turnofacil.salta.service.ISpecialityService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SpecialityServiceImpl implements ISpecialityService {
    private final SpecialityRepository specialityRepository;

    public SpecialityServiceImpl(SpecialityRepository specialityRepository){
        this.specialityRepository = specialityRepository;
    }

    @Override
    @Transactional
    public SpecialityResponseDTO createSpeciality(SpecialityRequestDTO requestDTO) {
        //si la especialidad ya existe en la base de datos
        if (specialityRepository.findByName(requestDTO.getName()).isPresent()){
            throw new IllegalArgumentException("Ya existe una especialidad con ese nombre. Recuerde que las especialidades son unicas");
        }

        Speciality speciality = new Speciality();
        speciality.setName(requestDTO.getName());
        speciality.setDescription(requestDTO.getDescription());

        Speciality savedSpeciality = specialityRepository.save(speciality);

        return SpecialityMapper.toSpecialityResponseDTO(savedSpeciality);
    }

    @Override
    @Transactional(readOnly = true)
    public List<SpecialityResponseDTO> getAllSpecialities() {
        List<Speciality> specialities = specialityRepository.findAll();
        return specialities.stream()
                .map(SpecialityMapper::toSpecialityResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public SpecialityResponseDTO getSpecialityById(Integer id) {
        Speciality speciality = specialityRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Speciality", "id", id));
        return SpecialityMapper.toSpecialityResponseDTO(speciality);
    }

    @Override
    @Transactional
    public SpecialityResponseDTO updateSpeciality(Integer id, SpecialityRequestDTO requestDTO) {
        Speciality speciality = specialityRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Speciality", "id", id));

        speciality.setName(requestDTO.getName());
        speciality.setDescription(requestDTO.getDescription());

        Speciality updatedSpeciality = specialityRepository.save(speciality);
        return SpecialityMapper.toSpecialityResponseDTO(updatedSpeciality);
    }

    @Override
    @Transactional
    public void deleteSpeciality(Integer id) {
        Speciality speciality = specialityRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Speciality", "id", id));

        specialityRepository.delete(speciality);
    }
}
