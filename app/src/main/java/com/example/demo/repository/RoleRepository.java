package com.example.demo.repository;

import com.example.demo.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    // Spring Data JPA fournit automatiquement les opérations CRUD

    // Ajout d'une méthode pour trouver un role par son name
    Optional<Role> findByName(String name);
}
