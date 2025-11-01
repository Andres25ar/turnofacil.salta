package com.turnofacil.salta.service.impl;

import com.turnofacil.salta.dto.admin.HealthCenterRequestDTO;
import com.turnofacil.salta.dto.admin.HealthCenterResponseDTO;
import com.turnofacil.salta.entity.HealthCenter;
import com.turnofacil.salta.entity.TypeOfCenter;
import com.turnofacil.salta.exception.ResourceNotFoundException;
import com.turnofacil.salta.mapper.HealthCenterMapper;
import com.turnofacil.salta.repository.HealthCenterRepository;
import com.turnofacil.salta.repository.TypeOfCenterRepository;
import com.turnofacil.salta.service.IHealthCenterService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service // Le dice a Spring que esta clase es un Servicio
public class HealthCenterServiceImpl implements IHealthCenterService {

    // --- Inyección de Dependencias por Constructor ---
    // Declaramos los repositorios que necesitamos.
    // Spring los "inyectará" automáticamente.
    private final HealthCenterRepository healthCenterRepository;
    private final TypeOfCenterRepository typeOfCenterRepository;

    //constructor
    public HealthCenterServiceImpl(HealthCenterRepository healthCenterRepository,
                                   TypeOfCenterRepository typeOfCenterRepository) {
        this.healthCenterRepository = healthCenterRepository;
        this.typeOfCenterRepository = typeOfCenterRepository;
    }

    // implementacion de metodos de la interfaz

    // Este metodo modifica la BD, por eso es transaccional
    @Override
    @Transactional
    public HealthCenterResponseDTO createHealthCenter(HealthCenterRequestDTO requestDTO) {
        /*
        * validar que el tipo de centro exista
        * buscar la entidad TypeOfCenter usando el ID que vino en el requestDTO
        * busca con el id del tipo de centro porque esto es requisito en el request de "HealthCenter"
        * * @NotNull(message = "El ID del tipo de centro es obligatorio")
        * * private Integer typeOfCenterId;
        */
        TypeOfCenter typeOfCenter = typeOfCenterRepository.findById(requestDTO.getTypeOfCenterId())
                                    .orElseThrow(() -> new ResourceNotFoundException("TypeOfCenter", "id", requestDTO.getTypeOfCenterId()));

        // validar que el nombre no esté repetido
        if (healthCenterRepository.findByCenterName(requestDTO.getCenterName()).isPresent()) {
            // !!! -- crear una excepción personalizada para esto despues
            throw new IllegalArgumentException("Ya existe un centro de salud con el nombre: " + requestDTO.getCenterName());
        }

        // mapear de DTO a entidad
        HealthCenter healthCenter = new HealthCenter();
        healthCenter.setCenterName(requestDTO.getCenterName());
        healthCenter.setCenterAddress(requestDTO.getCenterAddress());
        healthCenter.setLatitude(requestDTO.getLatitude());
        healthCenter.setLongitude(requestDTO.getLongitude());
        healthCenter.setPostalCode(requestDTO.getCp());
        healthCenter.setPhone(requestDTO.getPhone());
        healthCenter.setTypeOfCenter(typeOfCenter);

        // guardar en la db con el metodo save de repository
        HealthCenter savedHealthCenter = healthCenterRepository.save(healthCenter);

        // mapear de Entidad a DTO de Respuesta
        return HealthCenterMapper.toHealthCenterResponseDTO(savedHealthCenter);
    }

    @Override
    @Transactional(readOnly = true)
    public List<HealthCenterResponseDTO> getAllHealthCenters() {
        //List<HealthCenter> healthCenters = healthCenterRepository.findAll();
        List<HealthCenter> healthCenters = healthCenterRepository.findAllWithDetails();

        // la api straem de java mapea varias entidades en una lista
        return healthCenters.stream()
                .map(HealthCenterMapper::toHealthCenterResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public HealthCenterResponseDTO getHealthCenterById(Long id) {
        HealthCenter healthCenter = healthCenterRepository.findById(id) //busca con el metodo de repository
                                    .orElseThrow(() -> new ResourceNotFoundException("HealthCenter", "id", id));

        //mapea a una responseDTO y lo retorna
        return HealthCenterMapper.toHealthCenterResponseDTO(healthCenter);
    }

    @Override
    @Transactional
    public HealthCenterResponseDTO updateHealthCenterById(Long id, HealthCenterRequestDTO requestDTO) {
        // encontrar la entidad existente
        HealthCenter healthCenter = healthCenterRepository.findById(id) //busca con el metodo del repository
                                    .orElseThrow(() -> new ResourceNotFoundException("HealthCenter", "id", id));

        // validar y buscar el tipo de centro (si cambio)
        TypeOfCenter typeOfCenter = typeOfCenterRepository.findById(requestDTO.getTypeOfCenterId())
                                    .orElseThrow(() -> new ResourceNotFoundException("TypeOfCenter", "id", requestDTO.getTypeOfCenterId()));

        // actualizar la entidad con los datos del DTO
        healthCenter.setCenterName(requestDTO.getCenterName());
        healthCenter.setCenterAddress(requestDTO.getCenterAddress());
        healthCenter.setLatitude(requestDTO.getLatitude());
        healthCenter.setLongitude(requestDTO.getLongitude());
        healthCenter.setPostalCode(requestDTO.getCp());
        healthCenter.setPhone(requestDTO.getPhone());
        healthCenter.setTypeOfCenter(typeOfCenter);

        // guardar
        HealthCenter updatedHealthCenter = healthCenterRepository.save(healthCenter);

        // devolver la respuesta
        return HealthCenterMapper.toHealthCenterResponseDTO(updatedHealthCenter);
    }

    @Override
    @Transactional
    public void deleteHealthCenterById(Long id) {
        // asegurar que existe antes de borrarlo
        HealthCenter healthCenter = healthCenterRepository.findById(id)
                                    .orElseThrow(() -> new ResourceNotFoundException("HealthCenter", "id", id));

        // borrar la entidad
        healthCenterRepository.delete(healthCenter);    //otro metodo de repsitory
    }
}
