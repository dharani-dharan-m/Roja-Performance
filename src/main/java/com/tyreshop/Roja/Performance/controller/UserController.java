package com.tyreshop.Roja.Performance.controller;

import com.tyreshop.Roja.Performance.model.User;
import com.tyreshop.Roja.Performance.service.OtpService;
import com.tyreshop.Roja.Performance.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "*", maxAge = 3600)
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private OtpService otpService;

    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        return userService.getUserById(id)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/username/{username}")
    public ResponseEntity<User> getUserByUsername(@PathVariable String username) {
        return userService.getUserByUsername(username)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<User> getUserByEmail(@PathVariable String email) {
        return userService.getUserByEmail(email)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<?> createUser(
            @RequestBody User user,
            @RequestHeader(value = "X-User-Role", required = false) String requesterRoleHeader,
            @RequestHeader(value = "X-OTP-Verification", required = false) String otpVerificationToken) {
        try {
            if (!otpService.isVerified(user.getEmail(), "USER_CREATE", otpVerificationToken == null ? "" : otpVerificationToken)) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("OTP verification required for user creation");
            }
            User.Role requesterRole = parseRole(requesterRoleHeader);
            return ResponseEntity.status(HttpStatus.CREATED).body(userService.createUser(user, requesterRole));
        } catch (SecurityException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateUser(
            @PathVariable Long id,
            @RequestBody User user,
            @RequestHeader(value = "X-User-Role", required = false) String requesterRoleHeader,
            @RequestHeader(value = "X-OTP-Verification", required = false) String otpVerificationToken) {
        try {
            if (!otpService.isVerified(user.getEmail(), "USER_UPDATE", otpVerificationToken == null ? "" : otpVerificationToken)) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("OTP verification required for user update");
            }
            User.Role requesterRole = parseRole(requesterRoleHeader);
            return ResponseEntity.ok(userService.updateUser(id, user, requesterRole));
        } catch (SecurityException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

    private User.Role parseRole(String requesterRoleHeader) {
        if (requesterRoleHeader == null || requesterRoleHeader.isBlank()) {
            return User.Role.USER;
        }
        try {
            return User.Role.valueOf(requesterRoleHeader.trim().toUpperCase());
        } catch (IllegalArgumentException ex) {
            return User.Role.USER;
        }
    }
}
