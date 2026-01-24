import { Component, OnInit } from '@angular/core';
import { ApiService } from '../../services/api.service';
import { Reservation } from '../../models/data.models';

@Component({
    selector: 'app-my-reservations',
    templateUrl: './my-reservations.component.html'
})
export class MyReservationsComponent implements OnInit {
    reservations: Reservation[] = [];

    constructor(private apiService: ApiService) { }

    ngOnInit() {
        this.loadReservations();
    }

    loadReservations() {
        this.apiService.getMyReservations().subscribe(data => {
            this.reservations = data;
        });
    }

    cancel(id: number) {
        if (confirm('Annuler cette réservation ?')) {
            this.apiService.cancelReservation(id).subscribe(() => {
                this.loadReservations();
            });
        }
    }
}
