package com.padel.backend.service.impl;

import com.padel.backend.entity.*;
import com.padel.backend.repository.CreneauRepository;
import com.padel.backend.repository.ReservationRepository;
import com.padel.backend.repository.TerrainRepository;
import com.padel.backend.repository.UtilisateurRepository;
import com.padel.backend.service.AdherentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AdherentServiceImpl implements AdherentService {

    private final TerrainRepository terrainRepository;
    private final CreneauRepository creneauRepository;
    private final ReservationRepository reservationRepository;
    private final UtilisateurRepository utilisateurRepository;

    @Override
    public List<Terrain> getAvailableTerrains() {
        // Logique simplifiée : retourner tous les terrains.
        // Une logique plus complexe pourrait filtrer ceux qui ont au moins 1 créneau
        // libre.
        return terrainRepository.findAll();
    }

    @Override
    public List<Creneau> getAvailableCreneaux(Long terrainId) {
        return creneauRepository.findAvailableByTerrainId(terrainId);
    }

    @Override
    public Reservation makeReservation(String userEmail, Long creneauId) {
        Utilisateur user = utilisateurRepository.findByEmail(userEmail)
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));

        Creneau creneau = creneauRepository.findById(creneauId)
                .orElseThrow(() -> new RuntimeException("Créneau non trouvé"));

        // Vérifier si le créneau est déjà réservé par quelqu'un d'autre (double check)
        // Note: La contrainte Unique sur le OneToOne dans l'entité Reservation aide,
        // mais une vérification explicite est mieux.

        Reservation reservation = Reservation.builder()
                .utilisateur(user)
                .terrain(creneau.getTerrain()) // Le créneau est lié à un terrain
                .creneau(creneau)
                .statut(Statut.EN_ATTENTE)
                .build();

        return reservationRepository.save(reservation);
    }

    @Override
    public List<Reservation> getMyReservations(String userEmail) {
        Utilisateur user = utilisateurRepository.findByEmail(userEmail)
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));
        return reservationRepository.findByUtilisateurId(user.getId());
    }

    @Override
    public void cancelReservation(Long id, String userEmail) {
        Reservation reservation = reservationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Réservation introuvable"));

        if (!reservation.getUtilisateur().getEmail().equals(userEmail)) {
            throw new RuntimeException("Accès refusé : vous ne pouvez annuler que vos propres réservations");
        }

        reservationRepository.delete(reservation);
    }
}
