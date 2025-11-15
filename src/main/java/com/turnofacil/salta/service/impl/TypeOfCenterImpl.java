package com.turnofacil.salta.service.impl;

import com.turnofacil.salta.dto.admin.TypeOfCenterResponseDTO;
import com.turnofacil.salta.entity.TypeOfCenter;
import com.turnofacil.salta.mapper.TypeOfCenterMapper;
import com.turnofacil.salta.repository.TypeOfCenterRepository;
import com.turnofacil.salta.service.ITypeOfCenterService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TypeOfCenterImpl implements ITypeOfCenterService {
    private final TypeOfCenterRepository typeOfCenterRepository;

    public TypeOfCenterImpl(TypeOfCenterRepository typeOfCenterRepository) {
        this.typeOfCenterRepository = typeOfCenterRepository;
    }

    @Override
    @Transactional
    public List<TypeOfCenterResponseDTO> getAllTypes() {
        List<TypeOfCenter> types = typeOfCenterRepository.findAll();

        return types.stream()
                .map(TypeOfCenterMapper::toResponeDTO)
                .collect(Collectors.toList());
    }
}
