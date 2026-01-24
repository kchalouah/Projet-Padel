import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { Terrain, Creneau, Reservation } from '../models/data.models';

@Injectable({
    providedIn: 'root'
})
export class ApiService {
    private apiUrl = environment.apiUrl;

    constructor(private http: HttpClient) { }

    // --- Admin Terrains ---
    getAllTerrains(): Observable<Terrain[]> {
        return this.http.get<Terrain[]>(`${this.apiUrl}/admin/terrains`);
    }

    createTerrain(terrain: Terrain): Observable<Terrain> {
        return this.http.post<Terrain>(`${this.apiUrl}/admin/terrains`, terrain);
    }

    updateTerrain(id: number, terrain: Terrain): Observable<Terrain> {
        return this.http.put<Terrain>(`${this.apiUrl}/admin/terrains/${id}`, terrain);
    }

    deleteTerrain(id: number): Observable<void> {
        return this.http.delete<void>(`${this.apiUrl}/admin/terrains/${id}`);
    }

    // --- Adherent ---
    getAvailableTerrains(): Observable<Terrain[]> {
        return this.http.get<Terrain[]>(`${this.apiUrl}/adherent/terrains`);
    }

    getAvailableCreneaux(terrainId: number): Observable<Creneau[]> {
        return this.http.get<Creneau[]>(`${this.apiUrl}/adherent/terrains/${terrainId}/creneaux`);
    }

    makeReservation(payload: any): Observable<Reservation> {
        return this.http.post<Reservation>(`${this.apiUrl}/adherent/reservations`, payload);
    }

    getMyReservations(): Observable<Reservation[]> {
        return this.http.get<Reservation[]>(`${this.apiUrl}/adherent/reservations`);
    }

    cancelReservation(id: number): Observable<void> {
        return this.http.delete<void>(`${this.apiUrl}/adherent/reservations/${id}`);
    }

    // --- Admin Stats & Slots ---
    getAdminStats(): Observable<any> {
        return this.http.get<any>(`${this.apiUrl}/admin/stats`);
    }

    addCreneau(terrainId: number, slot: any): Observable<Creneau> {
        return this.http.post<Creneau>(`${this.apiUrl}/admin/creneaux`, null, {
            params: {
                terrainId: terrainId.toString(),
                date: slot.date,
                start: slot.start,
                end: slot.end
            }
        });
    }

    getSlotsByTerrainAdmin(terrainId: number): Observable<Creneau[]> {
        return this.http.get<Creneau[]>(`${this.apiUrl}/admin/creneaux/terrain/${terrainId}`);
    }

    getAllReservationsAdmin(): Observable<Reservation[]> {
        return this.http.get<Reservation[]>(`${this.apiUrl}/admin/reservations`);
    }

    validateReservation(id: number): Observable<Reservation> {
        return this.http.put<Reservation>(`${this.apiUrl}/admin/reservations/${id}/valider`, {});
    }

    refuseReservation(id: number): Observable<Reservation> {
        return this.http.put<Reservation>(`${this.apiUrl}/admin/reservations/${id}/refuser`, {});
    }

    deleteCreneau(id: number): Observable<void> {
        return this.http.delete<void>(`${this.apiUrl}/admin/creneaux/${id}`);
    }
}
