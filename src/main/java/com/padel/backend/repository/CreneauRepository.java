package com.padel.backend.repository;

import com.padel.backend.entity.Creneau;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CreneauRepository extends JpaRepository<Creneau, Long> {
    List<Creneau> findByTerrainId(Long terrainId);

    // Trouver les créneaux qui ne sont PAS dans la table réservation (disponibles)
    @Query("SELECT c FROM Creneau c WHERE c.terrain.id = :terrainId AND c.id NOT IN (SELECT r.creneau.id FROM Reservation r)")
    List<Creneau> findAvailableByTerrainId(@Param("terrainId") Long terrainId);
}
