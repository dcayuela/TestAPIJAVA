package com.example.demo.config;

import com.example.demo.entity.Role;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.CustomUserDetailsService;

import lombok.RequiredArgsConstructor;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.http.HttpMethod;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

// Pour le gestion du CORS
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.CorsRegistry;



@Configuration(proxyBeanMethods = false)
@RequiredArgsConstructor
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    private final CustomUserDetailsService userDetailsService;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // DaoAuthenticationProvider pour vérifier login/mot de passe dans la DB
    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService);
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }

    // AuthenticationManager nécessaire pour login manuel si besoin
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    // SecurityFilterChain : endpoints sécurisés
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
            .csrf(csrf -> csrf.disable()) // Désactive la protection CSRF pour les API REST
            .authorizeHttpRequests(auth -> auth
		// Règles pour le login
		.requestMatchers("/api/login/**").permitAll()

                // Règles pour les produits
                // GET /api/products et GET /api/products/{id} sont accessibles à tous les utilisateurs authentifiés (USER ou ADMIN)
                // Si vous voulez que tout le monde (même non authentifié) puisse lire les produits, utilisez .permitAll()
                .requestMatchers(HttpMethod.GET, "/api/products", "/api/products/**").permitAll()
                // POST, PUT, DELETE /api/products sont réservés aux USER ou ADMIN
                .requestMatchers(HttpMethod.POST, "/api/products").hasAnyRole("USER", "ADMIN")
                .requestMatchers(HttpMethod.PUT, "/api/products/**").hasAnyRole("USER", "ADMIN")
                .requestMatchers(HttpMethod.DELETE, "/api/products/**").hasAnyRole("USER", "ADMIN")

                // Règles pour la gestion des utilisateurs
                // Toutes les opérations sur /api/users sont réservées aux ADMIN
                .requestMatchers("/api/users/**").hasRole("ADMIN")

                // Toutes les autres requêtes non spécifiées nécessitent une authentification
                .anyRequest().authenticated()
            ) // Fin du lambda authorizeHttpRequests
            .httpBasic(Customizer.withDefaults()); // Active l'authentification basique HTTP

        return http.build();
    }


    // Spring Boot doit autoriser le frontend WEBà accéder aux APIs :
    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/api/**")
                    .allowedOrigins("http://192.168.50.10:4200")  // Angular URL. Doit correspondre à l’URL que AngularJS utilise depuis le conteneur
		    //.allowedOriginPatterns("*")
                    .allowedMethods("GET","POST","PUT","DELETE")
		    .allowedHeaders("*")
		    .allowCredentials(true);
            }
        };
    }
}
