package com.example.demo.service;

import com.example.demo.entity.Role;
import com.example.demo.entity.User;
import com.example.demo.repository.RoleRepository;
import com.example.demo.repository.UserRepository;

import lombok.RequiredArgsConstructor;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }

    public User createUser(User user, Optional<String> roleName) {
        // Vérifier si l'utilisateur existe déjà
        if (userRepository.findByUsername(user.getUsername()).isPresent()) {
            throw new IllegalArgumentException("User with username " + user.getUsername() + " already exists.");
        }

        // Encoder le mot de passe avant de sauvegarder
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        // Assurez-vous que les rôles existent ou créez-les si nécessaire
        Set<Role> managedRoles = new HashSet<>();
	if (roleName.isPresent()) {
            Role role = roleRepository.findByName(roleName.get())
                    .orElseThrow(() -> new RuntimeException("Role not found"));
            managedRoles.add(role);
        } else {
            Role defaultRole = roleRepository.findByName("USER")
                    .orElseThrow(() -> new RuntimeException("Role USER not found"));
            managedRoles.add(defaultRole);
        }

        user.setRoles(managedRoles);

        return userRepository.save(user);
    }

    public User updateUser(Long id, User userDetails) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found for this id :: " + id));

        user.setUsername(userDetails.getUsername());
        // Mettre à jour le mot de passe seulement s'il est fourni et différent
        if (userDetails.getPassword() != null && !userDetails.getPassword().isEmpty()) {
            user.setPassword(passwordEncoder.encode(userDetails.getPassword()));
        }

        // Mettre à jour les rôles
        Set<Role> managedRoles = new HashSet<>();
        if (userDetails.getRoles() != null) {
            for (Role role : userDetails.getRoles()) {
                Role existingRole = roleRepository.findByName(role.getName())
                        .orElseGet(() -> roleRepository.save(new Role(role.getName())));
                managedRoles.add(existingRole);
            }
        }
        user.setRoles(managedRoles);

        return userRepository.save(user);
    }

    public void deleteUser(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found for this id :: " + id));
        userRepository.delete(user);
    }
}
