package com.turnofacil.salta.repository;

import com.turnofacil.salta.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {
    // SELECT * FROM role WHERE role_name = ?
    Optional<Role> findByRoleName(String roleName);
}
