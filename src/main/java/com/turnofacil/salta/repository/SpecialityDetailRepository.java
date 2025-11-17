package com.turnofacil.salta.repository;

import com.turnofacil.salta.entity.HealthCenter;
import com.turnofacil.salta.entity.Professional;
import com.turnofacil.salta.entity.Speciality;
import com.turnofacil.salta.entity.SpecialityDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SpecialityDetailRepository extends JpaRepository<SpecialityDetail, Long> {

    // busquedas combinadas que serán muy útiles

    // buscar todas las entradas por profesional
    List<SpecialityDetail> findByProfessional(Professional professional);

    // buscar todas las entradas por centro de salud
    List<SpecialityDetail> findByHealthCenter(HealthCenter healthCenter);

    // buscar entradas por centro Y especialidad (ej. "taumatologos del Hospital San Bernardo")
    List<SpecialityDetail> findByHealthCenterAndSpeciality(HealthCenter healthCenter, Speciality speciality);

    //busca entradas por cenro y especialidad, pero comprueba que este aciva
    @Query("SELECT sd FROM SpecialityDetail sd " +
            "JOIN FETCH sd.professional p " +
            "JOIN FETCH p.user " +
            "JOIN FETCH sd.schedule " +
            "WHERE sd.healthCenter = :healthCenter " +
            "AND sd.speciality = :speciality " +
            "AND sd.status = true")
    List<SpecialityDetail> findActiveDetailsByCenterAndSpeciality (
            @Param("healthCenter") HealthCenter healthCenter,
            @Param("speciality") Speciality speciality);

    @Query("SELECT sd FROM SpecialityDetail sd " +
            "JOIN FETCH sd.healthCenter " +
            "JOIN FETCH sd.speciality " +
            "JOIN FETCH sd.professional p " +
            "JOIN FETCH p.user " +
            "JOIN FETCH sd.schedule")
    List<SpecialityDetail> findAllWithDetails();

    Optional<SpecialityDetail> findByHealthCenterAndSpecialityAndProfessional(
            HealthCenter healthCenter, Speciality speciality, Professional professional
    );


}