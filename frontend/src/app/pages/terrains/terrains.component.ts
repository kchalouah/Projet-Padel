import { Component, OnInit } from '@angular/core';
import { ApiService } from '../../services/api.service';
import { AuthService } from '../../services/auth.service';
import { Terrain } from '../../models/data.models';

@Component({
    selector: 'app-terrains',
    templateUrl: './terrains.component.html'
})
export class TerrainsComponent implements OnInit {
    terrains: Terrain[] = [];
    newTerrain: Terrain = { nom: '', description: '', localisation: '', etat: 'Bon', prix: 20 };
    showForm = false;

    selectedTerrainId: number | null = null;
    slotForm = { date: '', start: '', end: '' };

    constructor(private apiService: ApiService, public authService: AuthService) { }

    ngOnInit() {
        this.loadTerrains();
    }

    loadTerrains() {
        const role = this.authService.getUserRole();
        if (role === 'ADMIN') {
            this.apiService.getAllTerrains().subscribe(data => {
                this.terrains = data;
            });
        } else {
            this.apiService.getAvailableTerrains().subscribe(data => {
                this.terrains = data;
            });
        }
    }

    addTerrain() {
        this.apiService.createTerrain(this.newTerrain).subscribe(() => {
            this.loadTerrains();
            this.showForm = false;
            this.newTerrain = { nom: '', description: '', localisation: '', etat: 'Bon', prix: 20 };
        });
    }

    deleteTerrain(id: number) {
        if (confirm('Êtes-vous sûr ?')) {
            this.apiService.deleteTerrain(id).subscribe(() => {
                this.loadTerrains();
            });
        }
    }

    openSlotForm(id: number) {
        this.selectedTerrainId = id;
    }

    addSlot() {
        if (this.selectedTerrainId) {
            this.apiService.addCreneau(this.selectedTerrainId, this.slotForm).subscribe(() => {
                alert('Créneau ajouté !');
                this.selectedTerrainId = null;
                this.slotForm = { date: '', start: '', end: '' };
            });
        }
    }
}
