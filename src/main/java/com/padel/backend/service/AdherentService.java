package com.padel.backend.service;

import com.padel.backend.entity.Creneau;
import com.padel.backend.entity.Reservation;
import com.padel.backend.entity.Terrain;

import java.util.List;

public interface AdherentService {
    List<Terrain> getAvailableTerrains();

    List<Creneau> getAvailableCreneaux(Long terrainId);

    Reservation makeReservation(String userEmail, Long creneauId);

    List<Reservation> getMyReservations(String userEmail);

    void cancelReservation(Long id, String userEmail);
}
