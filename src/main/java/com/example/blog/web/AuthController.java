package com.example.blog.web;

import com.example.blog.service.auth.AuthService;
import com.example.blog.web.dto.LoginRequest;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/admin/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody @Valid LoginRequest loginRequest,
            HttpServletRequest request,
            HttpServletResponse response) {
        authService.login(loginRequest.getUsername(), loginRequest.getPassword(), request, response);
        return ResponseEntity.ok(Map.of("message", "Login successful"));
    }

    @GetMapping("/me")
    public ResponseEntity<?> checkAuth(org.springframework.security.core.Authentication authentication) {
        if (authentication == null || !authentication.isAuthenticated() ||
                authentication instanceof org.springframework.security.authentication.AnonymousAuthenticationToken) {
            return ResponseEntity.status(401).build();
        }
        return ResponseEntity
                .ok(Map.of("username", authentication.getName(), "roles", authentication.getAuthorities()));
    }

    // Logout is handled by SecurityConfig, but explicit defining is fine too if we
    // need custom logic.
}
