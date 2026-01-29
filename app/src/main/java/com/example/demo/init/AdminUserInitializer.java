package com.example.demo.init;

import com.example.demo.entity.Role;
import com.example.demo.entity.User;
import com.example.demo.repository.RoleRepository;
import com.example.demo.repository.UserRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
@RequiredArgsConstructor // Génère le constructeur
@Slf4j     // Génère automatiquement le logger 'log'
@Order(2)  // Exécuté après RoleInitializer
public class AdminUserInitializer implements ApplicationRunner {

    private final UserRepository userRepo;
    private final RoleRepository roleRepo;
    private final PasswordEncoder encoder;

    @Value("${ADMIN_DEFAULT_PASSWORD}")
    private String adminPassword;


    @Override
    public void run(ApplicationArguments args) {

        Role adminRole = roleRepo.findByName("ADMIN")
		.orElseThrow(() -> new IllegalStateException("ROLE ADMIN not found"));

	userRepo.findByUsername("admin").ifPresentOrElse(
            user -> log.info("Admin user already exists"),
            () -> createAdmin(adminRole)
        );
    }

    private void createAdmin(Role adminRole) {
        User admin = new User();
        admin.setUsername("admin");
        admin.setPassword(encoder.encode(adminPassword));

	// Récuperer le rôle attaché depuis le Repository pour éviter l'entité détachée
	Role managedRole = roleRepo.findById(adminRole.getId())
		.orElseThrow(() -> new IllegalStateException("ROLE ADMIN not managed"));

        admin.getRoles().add(managedRole);

        userRepo.save(admin);  // OK, rôle attaché

	log.info("Admin user created with role ADMIN");
    }
}

