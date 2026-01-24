package com.padel.backend.service.impl;

import com.padel.backend.entity.Creneau;
import com.padel.backend.entity.Reservation;
import com.padel.backend.entity.Statut;
import com.padel.backend.entity.Terrain;
import com.padel.backend.repository.CreneauRepository;
import com.padel.backend.repository.ReservationRepository;
import com.padel.backend.repository.TerrainRepository;
import com.padel.backend.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {

    private final TerrainRepository terrainRepository;
    private final CreneauRepository creneauRepository;
    private final ReservationRepository reservationRepository;
    private final com.padel.backend.repository.UtilisateurRepository utilisateurRepository;

    @Override
    public Terrain createTerrain(Terrain terrain) {
        return terrainRepository.save(terrain);
    }

    @Override
    public Terrain updateTerrain(Long id, Terrain terrainDetails) {
        Terrain terrain = terrainRepository.findById(id).orElseThrow(() -> new RuntimeException("Terrain non trouvé"));
        terrain.setNom(terrainDetails.getNom());
        terrain.setDescription(terrainDetails.getDescription());
        terrain.setLocalisation(terrainDetails.getLocalisation());
        terrain.setEtat(terrainDetails.getEtat());
        terrain.setPrix(terrainDetails.getPrix());
        return terrainRepository.save(terrain);
    }

    @Override
    public void deleteTerrain(Long id) {
        terrainRepository.deleteById(id);
    }

    @Override
    public List<Terrain> getAllTerrains() {
        return terrainRepository.findAll();
    }

    @Override
    public Creneau createCreneau(Long terrainId, LocalDate date, LocalTime start, LocalTime end) {
        Terrain terrain = terrainRepository.findById(terrainId)
                .orElseThrow(() -> new RuntimeException("Terrain non trouvé"));
        Creneau creneau = Creneau.builder()
                .terrain(terrain)
                .date(date)
                .heureDebut(start)
                .heureFin(end)
                .build();
        return creneauRepository.save(creneau);
    }

    @Override
    public void deleteCreneau(Long id) {
        creneauRepository.deleteById(id);
    }

    @Override
    public List<Creneau> getAllCreneaux() {
        return creneauRepository.findAll();
    }

    @Override
    public List<Reservation> getAllReservations() {
        return reservationRepository.findAll();
    }

    @Override
    public Reservation validateReservation(Long id) {
        Reservation reservation = reservationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Réservation non trouvée"));
        reservation.setStatut(Statut.VALIDEE);
        return reservationRepository.save(reservation);
    }

    @Override
    public Reservation refuseReservation(Long id) {
        Reservation reservation = reservationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Réservation non trouvée"));
        reservation.setStatut(Statut.REFUSEE);
        return reservationRepository.save(reservation);
    }

    @Override
    public List<com.padel.backend.entity.Utilisateur> getAllUsers() {
        return utilisateurRepository.findAll();
    }

    @Override
    public void deleteUser(Long id) {
        utilisateurRepository.deleteById(id);
    }

    @Override
    public List<Creneau> getCreneauxByTerrain(Long terrainId) {
        List<Creneau> creneaux = creneauRepository.findByTerrainId(terrainId);
        List<Long> reservedCreneauIds = reservationRepository.findAll().stream()
                .map(r -> r.getCreneau().getId())
                .toList();

        creneaux.forEach(c -> c.setReserve(reservedCreneauIds.contains(c.getId())));
        return creneaux;
    }

    @Override
    public com.padel.backend.dto.DashboardStatsDTO getDashboardStats() {
        return com.padel.backend.dto.DashboardStatsDTO.builder()
                .totalUsers(utilisateurRepository.count())
                .totalReservations(reservationRepository.count())
                .totalTerrains(terrainRepository.count())
                .recentReservations(reservationRepository.findAll())
                .build();
    }
}
