package com.padel.backend.service.impl;

import com.padel.backend.dto.AuthenticationRequest;
import com.padel.backend.dto.AuthenticationResponse;
import com.padel.backend.dto.RegisterRequest;
import com.padel.backend.entity.Role;
import com.padel.backend.entity.Utilisateur;
import com.padel.backend.repository.UtilisateurRepository;
import com.padel.backend.security.JwtService;
import com.padel.backend.service.AuthService;
import com.padel.backend.service.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

        private final UtilisateurRepository repository;
        private final PasswordEncoder passwordEncoder;
        private final JwtService jwtService;
        private final AuthenticationManager authenticationManager;
        private final EmailService emailService;

        @Override
        public void register(RegisterRequest request) {
                // Génération automatique du mot de passe
                String generatedPassword = UUID.randomUUID().toString().substring(0, 8); // 8 chars simple pass

                var user = Utilisateur.builder()
                                .nom(request.getNom())
                                .prenom(request.getPrenom())
                                .email(request.getEmail())
                                .telephone(request.getTelephone())
                                .password(passwordEncoder.encode(generatedPassword))
                                .role(Role.ADHERENT)
                                .build();
                repository.save(user);

                // Envoi du mot de passe par email
                emailService.sendPassword(request.getEmail(), generatedPassword);
        }

        @Override
        public AuthenticationResponse login(AuthenticationRequest request) {
                try {
                        authenticationManager.authenticate(
                                        new UsernamePasswordAuthenticationToken(
                                                        request.getEmail(),
                                                        request.getPassword()));
                        var user = repository.findByEmail(request.getEmail())
                                        .orElseThrow();
                        var jwtToken = jwtService.generateToken(user);
                        return AuthenticationResponse.builder()
                                        .token(jwtToken)
                                        .role(user.getRole())
                                        .nom(user.getNom())
                                        .prenom(user.getPrenom())
                                        .build();
                } catch (Exception e) {
                        e.printStackTrace();
                        throw e;
                }
        }

        @Override
        public void changePassword(String email, com.padel.backend.dto.ChangePasswordRequest request) {
                var user = repository.findByEmail(email)
                                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));

                if (!passwordEncoder.matches(request.getCurrentPassword(), user.getPassword())) {
                        throw new RuntimeException("Mot de passe actuel incorrect");
                }

                user.setPassword(passwordEncoder.encode(request.getNewPassword()));
                repository.save(user);
        }
}
