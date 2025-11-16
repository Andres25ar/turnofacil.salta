package com.turnofacil.salta.repository;

import com.turnofacil.salta.entity.Speciality;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SpecialityRepository extends JpaRepository<Speciality, Integer> {

    //buscar una especialidad por su nombre
    Optional<Speciality> findByName(String name);

    @Query("SELECT DISTINCT s FROM Speciality s " +
            "JOIN s.specialityDetails sd " +
            "WHERE sd.healthCenter.id = :centerId AND sd.status = true")
    List<Speciality> findDistinctByHealthCenterId(@Param("centerId") Long centerId);
}