package com.padel.backend.controller;

import com.padel.backend.dto.AuthenticationRequest;
import com.padel.backend.dto.AuthenticationResponse;
import com.padel.backend.dto.RegisterRequest;
import com.padel.backend.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<java.util.Map<String, String>> register(@RequestBody RegisterRequest request) {
        authService.register(request);
        return ResponseEntity
                .ok(java.util.Map.of("message", "Inscription réussie. Vérifiez vos emails pour le mot de passe."));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> login(@RequestBody AuthenticationRequest request) {
        return ResponseEntity.ok(authService.login(request));
    }

    @PostMapping("/change-password")
    public ResponseEntity<java.util.Map<String, String>> changePassword(
            @RequestBody com.padel.backend.dto.ChangePasswordRequest request,
            java.security.Principal principal) {
        authService.changePassword(principal.getName(), request);
        return ResponseEntity.ok(java.util.Map.of("message", "Mot de passe mis à jour avec succès"));
    }
}
