package com.example.demo.controller;

import lombok.RequiredArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/login")
@RequiredArgsConstructor
public class LoginController {

    private final AuthenticationManager authenticationManager;


    @PostMapping("/check")
    public void checkLogin(@RequestBody LoginRequest request) {
        try {
            authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
            );
            // Si authentifié → 200 OK (rien à renvoyer)
        } catch (AuthenticationException e) {
            throw new RuntimeException("Login ou mot de passe incorrect", e);
        }
    }

    // Classe interne pour capturer le JSON
    @Getter
    @Setter
    public static class LoginRequest {
        private String username;
        private String password;
    }
}

