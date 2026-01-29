package com.example.demo.repository;

import com.example.demo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    // Spring Data JPA fournit automatiquement les opérations CRUD

    // Ajout d'une méthode pour trouver un utilisateur par son username
    Optional<User> findByUsername(String username);

    // Ajout d'une méthode pour vérifier si un utilsateur (username) existe dans la table
    boolean existsByUsername(String username);
}
