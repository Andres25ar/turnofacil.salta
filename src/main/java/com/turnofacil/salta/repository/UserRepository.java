package com.turnofacil.salta.repository;

import com.turnofacil.salta.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    // Spring Data JPA crea la consulta automáticamente: SELECT * FROM user WHERE email = ?
    Optional<User> findByEmail(String email);

    // SELECT * FROM user WHERE cuil = ?
    Optional<User> findByCuil(String cuil);

    // SELECT * FROM user WHERE dni = ?
    Optional<User> findByDni(Long dni);

    // Comprueba si ya existe un usuario con ese email
    Boolean existsByEmail(String email);

    // Comprueba si ya existe un usuario con ese cuil
    Boolean existsByCuil(String cuil);

    // Comprueba si ya existe un usuario con ese dni
    Boolean existsByDni(Long dni);
}
