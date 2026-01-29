package com.example.demo.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.Components;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


// Pour personnaliser le titre, la description ou le versionnage de la génération du Swagger
// Swagger UI fonctionnel sur /swagger-ui.html ou /swagger-ui/index.html
// Documentation automatique pour tous les endpoints /api/products et /api/users
// Possibilité de tester les endpoints avec HTTP Basic.
// Pour les endpoints sécurisés, Swagger affichera un bouton “Authorize” où tu pourras entrer admin:admin123 ou user1:userpass selon le rôle.

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
            .components(new Components()
                .addSecuritySchemes("basicScheme",
                    new SecurityScheme()
                        .type(SecurityScheme.Type.HTTP)
                        .scheme("basic")))
            .addSecurityItem(new SecurityRequirement().addList("basicScheme"))
            .info(new Info()
                .title("API Produits & Users")
                .version("1.0")
                .description("API REST pour gérer les produits et les utilisateurs. Sécurisé avec HTTP Basic. GET produits accessibles à tous, POST/PUT/DELETE produits accessible aux USER/ADMIN et CRUD Users réservés aux ADMIN."));
    }
}

