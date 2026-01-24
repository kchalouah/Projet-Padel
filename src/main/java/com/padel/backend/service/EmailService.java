package com.padel.backend.service;

public interface EmailService {
    void sendPassword(String to, String password);
}
