package com.turnofacil.salta.repository;

import com.turnofacil.salta.entity.TypeOfCenter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TypeOfCenterRepository extends JpaRepository<TypeOfCenter, Integer> {

    // Para buscar un tipo por su nombre
    Optional<TypeOfCenter> findByType(String type);
}