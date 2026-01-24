package com.padel.backend.controller;

import com.padel.backend.entity.Creneau;
import com.padel.backend.entity.Reservation;
import com.padel.backend.entity.Terrain;
import com.padel.backend.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {

    private final AdminService adminService;

    // --- Terrains ---
    @PostMapping("/terrains")
    public ResponseEntity<Terrain> createTerrain(@RequestBody Terrain terrain) {
        return ResponseEntity.ok(adminService.createTerrain(terrain));
    }

    @PutMapping("/terrains/{id}")
    public ResponseEntity<Terrain> updateTerrain(@PathVariable Long id, @RequestBody Terrain terrain) {
        return ResponseEntity.ok(adminService.updateTerrain(id, terrain));
    }

    @DeleteMapping("/terrains/{id}")
    public ResponseEntity<Void> deleteTerrain(@PathVariable Long id) {
        adminService.deleteTerrain(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/terrains")
    public ResponseEntity<List<Terrain>> getAllTerrains() {
        return ResponseEntity.ok(adminService.getAllTerrains());
    }

    // --- Creneaux ---
    // Example: POST
    // /api/admin/creneaux?terrainId=1&date=2024-01-20&start=10:00&end=11:30
    @PostMapping("/creneaux")
    public ResponseEntity<Creneau> createCreneau(
            @RequestParam Long terrainId,
            @RequestParam String date,
            @RequestParam String start,
            @RequestParam String end) {
        return ResponseEntity.ok(
                adminService.createCreneau(
                        terrainId,
                        LocalDate.parse(date),
                        LocalTime.parse(start),
                        LocalTime.parse(end)));
    }

    @GetMapping("/creneaux")
    public ResponseEntity<List<Creneau>> getAllCreneaux() {
        return ResponseEntity.ok(adminService.getAllCreneaux());
    }

    @DeleteMapping("/creneaux/{id}")
    public ResponseEntity<Void> deleteCreneau(@PathVariable Long id) {
        adminService.deleteCreneau(id);
        return ResponseEntity.noContent().build();
    }

    // --- Reservations ---
    @GetMapping("/reservations")
    public ResponseEntity<List<Reservation>> getAllReservations() {
        return ResponseEntity.ok(adminService.getAllReservations());
    }

    @PutMapping("/reservations/{id}/valider")
    public ResponseEntity<Reservation> validateReservation(@PathVariable Long id) {
        return ResponseEntity.ok(adminService.validateReservation(id));
    }

    @PutMapping("/reservations/{id}/refuser")
    public ResponseEntity<Reservation> refuseReservation(@PathVariable Long id) {
        return ResponseEntity.ok(adminService.refuseReservation(id));
    }

    @GetMapping("/stats")
    public ResponseEntity<com.padel.backend.dto.DashboardStatsDTO> getDashboardStats() {
        return ResponseEntity.ok(adminService.getDashboardStats());
    }

    // --- Users ---
    @GetMapping("/users")
    public ResponseEntity<List<com.padel.backend.entity.Utilisateur>> getAllUsers() {
        return ResponseEntity.ok(adminService.getAllUsers());
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        adminService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
}
