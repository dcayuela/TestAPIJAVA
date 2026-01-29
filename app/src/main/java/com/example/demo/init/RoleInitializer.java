package com.example.demo.init;

import com.example.demo.entity.Role;
import com.example.demo.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;


@Component
@Slf4j     // Génère automatiquement le logger 'log'
@Order(1)  // Exécuté avant l'AdminUserInitializer
@RequiredArgsConstructor  // génère le constructeur pour roleRepository
public class RoleInitializer implements ApplicationRunner {

    private final RoleRepository roleRepository;


    @Override
    public void run(ApplicationArguments args) {

        createRoleIfNotExists("ADMIN");
        createRoleIfNotExists("USER");
    }

    private void createRoleIfNotExists(String roleName) {
        roleRepository.findByName(roleName).ifPresentOrElse(
            r -> log.info("Role {} already exists", roleName),
            () -> {
                roleRepository.save(new Role(roleName));
                log.info("Role {} created", roleName);
            }
        );
    }
}

