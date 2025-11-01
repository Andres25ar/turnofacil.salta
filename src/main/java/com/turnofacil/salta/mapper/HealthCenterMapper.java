package com.turnofacil.salta.mapper;

import com.turnofacil.salta.dto.admin.HealthCenterResponseDTO;
import com.turnofacil.salta.entity.HealthCenter;

public class HealthCenterMapper {

    //recibe una entidad de la base de datos y devuelve una dto
    public static HealthCenterResponseDTO toHealthCenterResponseDTO(HealthCenter healthCenter){
        //los campos a retornar
        return new HealthCenterResponseDTO(
                healthCenter.getCenterId(),
                healthCenter.getCenterName(),
                healthCenter.getCenterAddress(),
                healthCenter.getLatitude(),
                healthCenter.getLongitude(),
                healthCenter.getPostalCode(),
                healthCenter.getPhone(),
                //type se llama el campo que guarda en tipo de centro (hospital, clinica, centro de salud, etc) en la entidad TypeOfCenter
                healthCenter.getTypeOfCenter().getType()
        );
    }
}
