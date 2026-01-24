package com.padel.backend.service;

import com.padel.backend.dto.AuthenticationRequest;
import com.padel.backend.dto.AuthenticationResponse;
import com.padel.backend.dto.RegisterRequest;

public interface AuthService {
    void register(RegisterRequest request);

    AuthenticationResponse login(AuthenticationRequest request);

    void changePassword(String email, com.padel.backend.dto.ChangePasswordRequest request);
}
