package com.example.demo.init;

import com.example.demo.entity.Product;
import com.example.demo.service.ProductService;

import lombok.RequiredArgsConstructor;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ProductTableInitializer implements ApplicationRunner {

    private final ProductService productService;

    @Override
    public void run(ApplicationArguments args) {
        // Vérifie si la table produits est vide
        List<Product> allProducts = productService.getAllProducts();
        if (allProducts.isEmpty()) {
            System.out.println("La table produits est vide. Ajout de produits par défaut...");

            // Création de produits par défaut
            Product p1 = new Product();
            p1.setName("Produit A");
            p1.setDescription("Description du produit A");
            p1.setPrice(10.0);

            Product p2 = new Product();
            p2.setName("Produit B");
            p2.setDescription("Description du produit B");
            p2.setPrice(15.0);

            Product p3 = new Product();
            p3.setName("Produit C");
            p3.setDescription("Description du produit C");
            p3.setPrice(20.0);

            // Sauvegarde via ProductService
            productService.createProduct(p1);
            productService.createProduct(p2);
            productService.createProduct(p3);

            System.out.println("Produits par défaut ajoutés.");
        } else {
            System.out.println("La table produits contient déjà des données. Aucun produit ajouté.");
        }
    }
}

