import { Component, OnInit } from '@angular/core';
import { ApiService } from '../../services/api.service';
import { Terrain, Creneau } from '../../models/data.models';
import { Router } from '@angular/router';

@Component({
    selector: 'app-reservation-book',
    templateUrl: './reservation-book.component.html'
})
export class ReservationBookComponent implements OnInit {
    terrains: Terrain[] = [];
    creneaux: Creneau[] = [];
    selectedTerrain: Terrain | null = null;
    message = '';

    constructor(private apiService: ApiService, private router: Router) { }

    ngOnInit() {
        this.apiService.getAvailableTerrains().subscribe(data => {
            this.terrains = data;
        });
    }

    selectTerrain(terrain: Terrain) {
        this.selectedTerrain = terrain;
        // Load available creneaux for this terrain
        // Note: Backend endpoint /adherent/terrains/{id}/creneaux is needed
        // Assuming backend endpoint exists as per AdminController logic or new AdherentController logic
        this.apiService.getAvailableCreneaux(terrain.id!).subscribe(data => {
            this.creneaux = data;
        });
    }

    book(creneau: Creneau) {
        if (confirm('Réserver ce créneau ?')) {
            this.apiService.makeReservation({
                creneauId: creneau.id,
                terrainId: this.selectedTerrain!.id
            }).subscribe({
                next: () => {
                    alert('Réservation effectuée !');
                    this.router.navigate(['/my-reservations']);
                },
                error: (err) => {
                    this.message = 'Erreur lors de la réservation';
                }
            });
        }
    }

    back() {
        this.selectedTerrain = null;
        this.creneaux = [];
    }
}
