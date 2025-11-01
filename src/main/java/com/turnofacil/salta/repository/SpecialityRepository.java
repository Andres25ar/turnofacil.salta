package com.turnofacil.salta.repository;

import com.turnofacil.salta.entity.Speciality;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SpecialityRepository extends JpaRepository<Speciality, Integer> {

    //buscar una especialidad por su nombre
    Optional<Speciality> findByName(String name);
}