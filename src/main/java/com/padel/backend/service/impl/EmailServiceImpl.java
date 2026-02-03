package com.padel.backend.service.impl;

import com.padel.backend.service.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {

    private final JavaMailSender javaMailSender;

    @org.springframework.beans.factory.annotation.Value("${spring.mail.username}")
    private String fromEmail;

    @Override
    public void sendPassword(String to, String password) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(fromEmail);
        message.setTo(to);
        message.setSubject("Bienvenue sur Padel Réservation - Vos identifiants");
        message.setText("Bonjour,\n\nVotre compte a été créé avec succès.\n" +
                "Voici votre mot de passe pour vous connecter : " + password + "\n\n" +
                "Vous pouvez le changer dès votre première connexion.\n\n" +
                "Cordialement,\nL'équipe Padel.");

        javaMailSender.send(message);
    }
}
