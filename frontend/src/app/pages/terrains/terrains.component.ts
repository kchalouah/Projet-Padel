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
    newTerrain: Terrain = { nom: '', description: '', localisation: '', etat: 'DISPONIBLE', prix: 40 };
    editMode = false;
    showForm = false;
    selectedTerrainId: number | null = null;
    slotForm = { date: '', start: '', end: '' };
    selectedTerrainSlots: any[] = [];
    showSlotsForId: number | null = null;

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

    onEdit(t: Terrain) {
        this.newTerrain = { ...t };
        this.editMode = true;
        this.showForm = true;
    }

    cancelEdit() {
        this.editMode = false;
        this.showForm = false;
        this.newTerrain = { nom: '', description: '', localisation: '', etat: 'DISPONIBLE', prix: 40 };
    }

    addTerrain() {
        if (this.editMode && this.newTerrain.id) {
            this.apiService.updateTerrain(this.newTerrain.id, this.newTerrain).subscribe(() => {
                this.loadTerrains();
                this.cancelEdit();
            });
        } else {
            this.apiService.createTerrain(this.newTerrain).subscribe(() => {
                this.loadTerrains();
                this.showForm = false;
                this.newTerrain = { nom: '', description: '', localisation: '', etat: 'DISPONIBLE', prix: 40 };
            });
        }
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
                if (this.showSlotsForId === this.selectedTerrainId) {
                    this.loadSlots(this.selectedTerrainId);
                }
                this.selectedTerrainId = null;
                this.slotForm = { date: '', start: '', end: '' };
            });
        }
    }

    loadSlots(id: number) {
        const role = this.authService.getUserRole();
        if (role === 'ADMIN') {
            this.apiService.getSlotsByTerrainAdmin(id).subscribe(data => {
                this.selectedTerrainSlots = data;
                this.showSlotsForId = id;
            });
        } else {
            this.apiService.getAvailableCreneaux(id).subscribe(data => {
                this.selectedTerrainSlots = data;
                this.showSlotsForId = id;
            });
        }
    }

    deleteSlot(id: number) {
        if (confirm('Supprimer ce créneau ?')) {
            this.apiService.deleteCreneau(id).subscribe(() => {
                if (this.showSlotsForId) this.loadSlots(this.showSlotsForId);
            });
        }
    }
}
