package com.padel.backend.controller;

import com.padel.backend.dto.ReservationRequest;
import com.padel.backend.entity.Creneau;
import com.padel.backend.entity.Reservation;
import com.padel.backend.entity.Terrain;
import com.padel.backend.service.AdherentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/adherent")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADHERENT')")
public class AdherentController {

    private final AdherentService adherentService;

    @GetMapping("/terrains")
    public ResponseEntity<List<Terrain>> getAvailableTerrains() {
        return ResponseEntity.ok(adherentService.getAvailableTerrains());
    }

    @GetMapping("/terrains/{terrainId}/creneaux")
    public ResponseEntity<List<Creneau>> getAvailableCreneaux(@PathVariable Long terrainId) {
        return ResponseEntity.ok(adherentService.getAvailableCreneaux(terrainId));
    }

    @PostMapping("/reservations")
    public ResponseEntity<Reservation> makeReservation(@RequestBody ReservationRequest request,
            Authentication authentication) {
        String userEmail = authentication.getName(); // Extracted from JWT
        return ResponseEntity.ok(adherentService.makeReservation(userEmail, request.getCreneauId()));
    }

    @GetMapping("/reservations")
    public ResponseEntity<List<Reservation>> getMyReservations(Authentication authentication) {
        return ResponseEntity.ok(adherentService.getMyReservations(authentication.getName()));
    }

    @DeleteMapping("/reservations/{id}")
    public ResponseEntity<Void> cancelReservation(@PathVariable Long id, Authentication authentication) {
        adherentService.cancelReservation(id, authentication.getName());
        return ResponseEntity.noContent().build();
    }
}
