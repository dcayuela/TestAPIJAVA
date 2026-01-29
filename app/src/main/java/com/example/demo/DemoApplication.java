package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

	// CommandLineRunner pour ajouter des données initiales si la base de données est vide
	/*
	@Bean

	public CommandLineRunner initData(UserRepository userRepository) {
		return args -> {
			if (userRepository.count() == 0) { // Ajoute seulement si aucun utilisateur n'existe
				userRepository.save(new User(null, "Alice", "alice@example.com", "123 Rue Principale"));
				userRepository.save(new User(null, "Bob", "bob@example.com", "456 Avenue Secondaire"));
				System.out.println("Utilisateurs initiaux ajoutés à la base de données.");
			} else {
				System.out.println("Des utilisateurs existent déjà dans la base de données, la création de données initiales est ignorée.");
			}
		};
	}
	*/
}
