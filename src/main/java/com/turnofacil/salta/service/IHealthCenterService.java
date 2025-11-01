package com.turnofacil.salta.service;

import com.turnofacil.salta.dto.admin.HealthCenterRequestDTO;
import com.turnofacil.salta.dto.admin.HealthCenterResponseDTO;

import java.util.List;

public interface IHealthCenterService {
    /*
    *crear un nuevo centro
    *metodo que recibe una requestDTO
    * retorna una responseDTO
    */
    HealthCenterResponseDTO createHealthCenter (HealthCenterRequestDTO requestDTO);

    /*
    * retornar los centros de atencion
    * debe retornar una responseDTO
    */
    List<HealthCenterResponseDTO> getAllHealthCenters();

    /*
     * retornar un centro de atencion
     * recibe un id (long) para buscarlo
     * debe retornar una responseDTO
     */
    HealthCenterResponseDTO getHealthCenterById (Long id);

    /*
    * actualiza un centro de atencion
    * recibe el id (long) del centro a actualizar
    * recibe una requestDTO con la nueva informacion
    * devuelve una responseDTO con la informacion actualizada
    */
    HealthCenterResponseDTO updateHealthCenterById (Long id, HealthCenterRequestDTO requestDTO);

    /*
     * elimina un centro de atencion
     * recibe un id (long) para buscarlo
     */
    void deleteHealthCenterById (Long id);
}
