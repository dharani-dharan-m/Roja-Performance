package com.tyreshop.Roja.Performance.service;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tyreshop.Roja.Performance.model.User;
import com.tyreshop.Roja.Performance.repository.UserRepository;
@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }
    public Optional<User> getUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }
    public Optional<User> getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }
    public User createUser(User user, User.Role requesterRole) {
        requireAdmin(requesterRole, "Only admins can create users and managers");
        validateUser(user, true);
        if (userRepository.existsByUsername(user.getUsername())) {
            throw new RuntimeException("Username already exists");
        }
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new RuntimeException("Email already exists");
        }
        if (user.getRole() == null) {
            user.setRole(User.Role.USER);
        }
        return userRepository.save(user);
    }

    public User updateUser(Long id, User user, User.Role requesterRole) {
        validateUser(user, false);
        return userRepository.findById(id).map(existingUser -> {
            userRepository.findByUsername(user.getUsername())
                    .filter(found -> !found.getId().equals(id))
                    .ifPresent(found -> {
                        throw new IllegalArgumentException("Username already exists");
                    });
            userRepository.findByEmail(user.getEmail())
                    .filter(found -> !found.getId().equals(id))
                    .ifPresent(found -> {
                        throw new IllegalArgumentException("Email already exists");
                    });
            existingUser.setUsername(user.getUsername());
            existingUser.setFullName(user.getFullName());
            existingUser.setEmail(user.getEmail());
            if (user.getRole() == User.Role.ADMIN || user.getRole() == User.Role.MANAGER) {
                requireAdmin(requesterRole, "Only admins can assign admin/manager roles");
            }
            existingUser.setRole(user.getRole() != null ? user.getRole() : existingUser.getRole());
            existingUser.setEnabled(user.getEnabled() != null ? user.getEnabled() : existingUser.getEnabled());
            return userRepository.save(existingUser);
        }).orElseThrow(() -> new RuntimeException("User not found"));
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    private void requireAdmin(User.Role requesterRole, String message) {
        if (requesterRole != User.Role.ADMIN) {
            throw new SecurityException(message);
        }
    }

    private void validateUser(User user, boolean passwordRequired) {
        if (user.getUsername() == null || user.getUsername().isBlank()) {
            throw new IllegalArgumentException("Username is required");
        }
        if (user.getEmail() == null || user.getEmail().isBlank()) {
            throw new IllegalArgumentException("User email is required");
        }
        if (user.getFullName() == null || user.getFullName().isBlank()) {
            throw new IllegalArgumentException("Full name is required");
        }
        if (passwordRequired && (user.getPassword() == null || user.getPassword().isBlank())) {
            throw new IllegalArgumentException("Password is required for new users");
        }
    }
}
