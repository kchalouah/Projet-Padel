# PadelTn - Système de Réservation de Padel

**PadelTn** est une application full-stack complète pour la gestion et la réservation de terrains de Padel, adaptée au contexte tunisien. Elle permet aux clubs de gérer leurs installations et aux adhérents de réserver leurs créneaux en toute simplicité.

## 🌟 Fonctionnalités Principales

### Pour les Administrateurs
- **Tableau de Bord** : Vue d'ensemble des statistiques (utilisateurs, terrains, réservations).
- **Gestion des Terrains** : Création, modification et suppression de terrains avec état et prix.
- **Gestion des Créneaux** : Ajout de disponibilités horaires par terrain.
- **Gestion des Réservations** : Validation ou refus des demandes de réservation.
- **Gestion des Utilisateurs** : Liste complète des inscrits et possibilité de suppression.

### Pour les Adhérents
- **Réservation en ligne** : Recherche de terrains et choix de créneaux disponibles.
- **Profil Personnel** : Gestion des informations et changement de mot de passe sécurisé.
- **Historique** : Consultation et suivi de l'état de ses propres réservations.

---

## 🛠️ Stack Technique

- **Backend** : Spring Boot 3, Java 17, Spring Security + JWT, Hibernate/JPA, MySQL, JavaMail.
- **Frontend** : Angular, Argon Dashboard Design System, TypeScript, SCSS.
- **Sécurité** : Authentification par token JWT, hachage des mots de passe (BCrypt), gestion de variables d'environnement.

---

## 🚀 Installation & Configuration

### 1. Backend (Spring Boot)
1.  **Base de données** : MySQL doit tourner sur le port `3306` (Schéma `padel`).
2.  **Variables d'environnement** : Créez un fichier `.env` à la racine pour vos identifiants SMTP :
    ```env
    SPRING_MAIL_USERNAME=votre-email@gmail.com
    SPRING_MAIL_PASSWORD=votre-mot-de-passe-application
    ```
3.  **Lancement** :
    ```bash
    mvn spring-boot:run
    ```

### 2. Frontend (Angular)
1.  **Installation** : 
    ```bash
    cd frontend
    npm install
    ```
2.  **Lancement** :
    ```bash
    ng serve
    ```
    L'application sera accessible sur `http://localhost:4200`.

---

## 📚 Documentation- Un jeu de données de test (Tunisian context) est injecté automatiquement au démarrage (`DataInitializer`).

---
&copy; 2026 PadelTn - Tous droits réservés.
