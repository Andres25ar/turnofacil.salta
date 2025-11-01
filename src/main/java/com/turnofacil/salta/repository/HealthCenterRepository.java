package com.turnofacil.salta.repository;

import com.turnofacil.salta.entity.HealthCenter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface HealthCenterRepository extends JpaRepository<HealthCenter, Long> {

    // buscar un centro por su nombre
    Optional<HealthCenter> findByCenterName(String centerName);

    /**
     * Busca todos los HealthCenters y trae sus relaciones TypeOfCenter
     * en una sola consulta para evitar el problema N+1.
     * "hc" es un alias para HealthCenter.
     * "JOIN FETCH hc.typeOfCenter" le dice a Hibernate que traiga la relación.
     */
    @Query("SELECT hc FROM HealthCenter hc JOIN FETCH hc.typeOfCenter")
    List<HealthCenter> findAllWithDetails();
}