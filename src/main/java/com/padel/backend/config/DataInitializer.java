package com.padel.backend.config;

import com.padel.backend.entity.Creneau;
import com.padel.backend.entity.Role;
import com.padel.backend.entity.Terrain;
import com.padel.backend.entity.Utilisateur;
import com.padel.backend.repository.CreneauRepository;
import com.padel.backend.repository.ReservationRepository;
import com.padel.backend.repository.TerrainRepository;
import com.padel.backend.repository.UtilisateurRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final UtilisateurRepository utilisateurRepository;
    private final TerrainRepository terrainRepository;
    private final CreneauRepository creneauRepository;
    private final ReservationRepository reservationRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {
        initUsers();
        initTerrains();
        initCreneaux();
        initReservations();
    }

    private void initUsers() {
        // Admins
        createAdmin("Admin", "Super", "admin@padel.com", "admin123");
        createAdmin("Tounsi", "Mohamed", "mohamed@padel.com", "manager123");

        // Adherents
        createAdherent("Ben Ali", "Ahmed", "ahmed@test.com", "user123");
        createAdherent("Trabelsi", "Yasmine", "yasmine@test.com", "user123");
        createAdherent("Jaziri", "Malek", "malek@padel.com", "user123");
        System.out.println("Users initialized.");
    }

    private void createAdmin(String nom, String prenom, String email, String password) {
        if (utilisateurRepository.findByEmail(email).isEmpty()) {
            Utilisateur admin = Utilisateur.builder()
                    .nom(nom).prenom(prenom).email(email)
                    .password(passwordEncoder.encode(password))
                    .telephone("20123456").role(Role.ADMIN).build();
            utilisateurRepository.save(admin);
            System.out.println("Admin created: " + email);
        }
    }

    private void createAdherent(String nom, String prenom, String email, String password) {
        if (utilisateurRepository.findByEmail(email).isEmpty()) {
            Utilisateur user = Utilisateur.builder()
                    .nom(nom).prenom(prenom).email(email)
                    .password(passwordEncoder.encode(password))
                    .telephone("50123456").role(Role.ADHERENT).build();
            utilisateurRepository.save(user);
            System.out.println("User created: " + email);
        }
    }

    private void initTerrains() {
        String[] localisations = { "Tunis - Berges du Lac", "Tunis - La Marsa", "Sousse - Kantaoui",
                "Hammamet - Yasmine", "Sfax - Route de Tunis" };
        for (int i = 1; i <= 5; i++) {
            String nom = "Terrain " + i;
            if (terrainRepository.findAll().stream().noneMatch(t -> t.getNom().equals(nom))) {
                Terrain t = Terrain.builder()
                        .nom(nom)
                        .description("Terrain professionnel " + (i % 2 == 0 ? "Indoor" : "Outdoor"))
                        .localisation(localisations[i - 1])
                        .etat("DISPONIBLE")
                        .prix(40.0 + (i * 5))
                        .build();
                terrainRepository.save(t);
            }
        }
        System.out.println("Terrains initialized.");
    }

    private void initCreneaux() {
        List<Terrain> terrains = terrainRepository.findAll();
        LocalDate today = LocalDate.now();
        // Create slots for next 7 days if they don't exist
        for (int i = 0; i < 7; i++) {
            LocalDate date = today.plusDays(i);
            for (Terrain t : terrains) {

                if (creneauRepository.findAll().stream()
                        .noneMatch(c -> c.getTerrain().getId().equals(t.getId()) && c.getDate().equals(date))) {
                    createSlot(t, date, LocalTime.of(10, 0), LocalTime.of(11, 30));
                    createSlot(t, date, LocalTime.of(14, 0), LocalTime.of(15, 30));
                    createSlot(t, date, LocalTime.of(18, 0), LocalTime.of(19, 30));
                }
            }
        }
        System.out.println("Creneaux initialized.");
    }

    private void createSlot(Terrain t, LocalDate date, LocalTime start, LocalTime end) {
        Creneau c = Creneau.builder()
                .terrain(t).date(date).heureDebut(start).heureFin(end).build();
        creneauRepository.save(c);
    }

    private void initReservations() {
        System.out.println("Initializing reservations...");
        Utilisateur ahmed = utilisateurRepository.findByEmail("ahmed@test.com").orElse(null);
        Utilisateur yasmine = utilisateurRepository.findByEmail("yasmine@test.com").orElse(null);
        List<Creneau> allSlots = creneauRepository.findAll();

        if (ahmed != null && allSlots.size() > 5) {
            createReservationIfMissing(ahmed, allSlots.get(0), com.padel.backend.entity.Statut.VALIDEE);
            createReservationIfMissing(ahmed, allSlots.get(1), com.padel.backend.entity.Statut.EN_ATTENTE);
        }

        if (yasmine != null && allSlots.size() > 10) {
            createReservationIfMissing(yasmine, allSlots.get(10), com.padel.backend.entity.Statut.VALIDEE);
        }

        System.out.println("Reservations initialized count: " + reservationRepository.count());
    }

    private void createReservationIfMissing(Utilisateur user, Creneau slot, com.padel.backend.entity.Statut statut) {
        if (reservationRepository.findAll().stream().noneMatch(r -> r.getCreneau().getId().equals(slot.getId()))) {
            com.padel.backend.entity.Reservation res = com.padel.backend.entity.Reservation.builder()
                    .utilisateur(user)
                    .terrain(slot.getTerrain())
                    .creneau(slot)
                    .statut(statut)
                    .dateReservation(java.time.LocalDateTime.now())
                    .build();
            reservationRepository.save(res);
            System.out.println("Reservation seeded for " + user.getEmail() + " on slot " + slot.getId());
        }
    }
}
