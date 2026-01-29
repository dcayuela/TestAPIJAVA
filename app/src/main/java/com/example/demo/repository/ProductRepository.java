package com.example.demo.repository;

import com.example.demo.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    // Spring Data JPA fournit automatiquement les opérations CRUD

    // Des méthodes de recherche personnlisées peuvent être ajoutées ici si nécessaire
}
