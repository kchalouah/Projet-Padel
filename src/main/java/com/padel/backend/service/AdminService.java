package com.padel.backend.service;

import com.padel.backend.entity.Creneau;
import com.padel.backend.entity.Reservation;
import com.padel.backend.entity.Terrain;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public interface AdminService {
    // Terrain CRUD
    Terrain createTerrain(Terrain terrain);

    Terrain updateTerrain(Long id, Terrain terrain);

    void deleteTerrain(Long id);

    List<Terrain> getAllTerrains();

    // Creneau CRUD
    Creneau createCreneau(Long terrainId, LocalDate date, LocalTime start, LocalTime end);

    void deleteCreneau(Long id);

    List<Creneau> getAllCreneaux();

    // Reservations
    List<Reservation> getAllReservations();

    Reservation validateReservation(Long id);

    Reservation refuseReservation(Long id);

    // Users
    List<com.padel.backend.entity.Utilisateur> getAllUsers();

    void deleteUser(Long id);

    com.padel.backend.dto.DashboardStatsDTO getDashboardStats();
}
