package com.turnofacil.salta.repository;

import com.turnofacil.salta.entity.HealthCenter;
import com.turnofacil.salta.entity.Professional;
import com.turnofacil.salta.entity.Speciality;
import com.turnofacil.salta.entity.SpecialityDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SpecialityDetailRepository extends JpaRepository<SpecialityDetail, Long> {

    // busquedas combinadas que serán muy útiles

    // buscar todas las entradas por profesional
    List<SpecialityDetail> findByProfessional(Professional professional);

    // buscar todas las entradas por centro de salud
    List<SpecialityDetail> findByHealthCenter(HealthCenter healthCenter);

    // buscar entradas por centro Y especialidad (ej. "taumatologos del Hospital San Bernardo")
    List<SpecialityDetail> findByHealthCenterAndSpeciality(HealthCenter healthCenter, Speciality speciality);
}