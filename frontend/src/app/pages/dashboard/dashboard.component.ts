import { Component, OnInit } from '@angular/core';
import Chart from 'chart.js';
import { AuthService } from '../../services/auth.service';
import { ApiService } from '../../services/api.service';
import { Reservation } from '../../models/data.models';

// core components
import {
  chartOptions,
  parseOptions,
  chartExample1,
  chartExample2
} from "../../variables/charts";

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.scss']
})
export class DashboardComponent implements OnInit {

  public role: string;
  public stats: any;
  public reservations: Reservation[] = [];

  constructor(private authService: AuthService, private apiService: ApiService) { }

  ngOnInit() {
    this.role = this.authService.getUserRole();
    if (this.role === 'ADMIN') {
      this.loadAdminStats();
    } else if (this.role === 'ADHERENT') {
      this.loadMyReservations();
    }
  }

  loadAdminStats() {
    this.apiService.getAdminStats().subscribe(
      data => this.stats = data,
      error => console.error('Error fetching admin stats', error)
    );
  }

  loadMyReservations() {
    this.apiService.getMyReservations().subscribe(
      data => this.reservations = data,
      error => console.error('Error fetching reservations', error)
    );
  }
}
