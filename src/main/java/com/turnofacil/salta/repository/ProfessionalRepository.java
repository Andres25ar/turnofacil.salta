package com.turnofacil.salta.repository;

import com.turnofacil.salta.entity.Professional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProfessionalRepository extends JpaRepository<Professional, Long> {

    //buscar un profesional por su matrícula (licence)
    Optional<Professional> findByLicence(String licence);
}

