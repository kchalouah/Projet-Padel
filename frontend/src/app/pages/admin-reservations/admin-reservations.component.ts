import { Component, OnInit } from '@angular/core';
import { ApiService } from '../../services/api.service';
import { Reservation } from '../../models/data.models';

@Component({
    selector: 'app-admin-reservations',
    templateUrl: './admin-reservations.component.html',
    styleUrls: ['./admin-reservations.component.scss']
})
export class AdminReservationsComponent implements OnInit {

    public reservations: Reservation[] = [];

    constructor(private apiService: ApiService) { }

    ngOnInit() {
        this.loadAllReservations();
    }

    loadAllReservations() {
        this.apiService.getAllReservationsAdmin().subscribe(
            data => this.reservations = data,
            error => console.error('Error loading admin reservations', error)
        );
    }

    validate(id: number) {
        this.apiService.validateReservation(id).subscribe(
            () => this.loadAllReservations(),
            error => console.error('Error validating reservation', error)
        );
    }

    refuse(id: number) {
        this.apiService.refuseReservation(id).subscribe(
            () => this.loadAllReservations(),
            error => console.error('Error refusing reservation', error)
        );
    }
}
