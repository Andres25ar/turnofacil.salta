package com.turnofacil.salta.repository;

import com.turnofacil.salta.entity.HealthCenter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface HealthCenterRepository extends JpaRepository<HealthCenter, Long> {

    // Para buscar un centro por su nombre
    Optional<HealthCenter> findByCenterName(String centerName);
}