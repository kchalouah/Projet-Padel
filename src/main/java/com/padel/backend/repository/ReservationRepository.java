package com.padel.backend.repository;

import com.padel.backend.entity.Reservation;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    @EntityGraph(attributePaths = { "utilisateur", "terrain", "creneau" })
    List<Reservation> findByUtilisateurId(Long utilisateurId);

    @Override
    @EntityGraph(attributePaths = { "utilisateur", "terrain", "creneau" })
    List<Reservation> findAll();
}
