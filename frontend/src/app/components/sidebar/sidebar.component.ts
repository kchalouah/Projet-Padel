import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { AuthService } from '../../services/auth.service';

declare interface RouteInfo {
  path: string;
  title: string;
  icon: string;
  class: string;
}
export const ROUTES: RouteInfo[] = [
  { path: '/dashboard', title: 'Dashboard', icon: 'ni-tv-2 text-primary', class: '' },
  { path: '/icons', title: 'Icons', icon: 'ni-planet text-blue', class: '' },
  { path: '/maps', title: 'Maps', icon: 'ni-pin-3 text-orange', class: '' },
  { path: '/user-profile', title: 'User profile', icon: 'ni-single-02 text-yellow', class: '' },
  { path: '/tables', title: 'Tables', icon: 'ni-bullet-list-67 text-red', class: '' },
  { path: '/login', title: 'Login', icon: 'ni-key-25 text-info', class: '' },
  { path: '/register', title: 'Register', icon: 'ni-circle-08 text-pink', class: '' }
];

@Component({
  selector: 'app-sidebar',
  templateUrl: './sidebar.component.html',
  styleUrls: ['./sidebar.component.scss']
})
export class SidebarComponent implements OnInit {

  public menuItems: any[];
  public isCollapsed = true;

  constructor(private router: Router, private authService: AuthService) { }

  ngOnInit() {
    this.updateMenu();
    this.router.events.subscribe((event) => {
      this.isCollapsed = true;
    });
  }

  updateMenu() {
    const role = this.authService.getUserRole();
    let routes = [];

    if (role === 'ADMIN') {
      routes = [
        { path: '/dashboard', title: 'Tableau de bord', icon: 'ni-tv-2 text-primary', class: '' },
        { path: '/terrains', title: 'Gérer Terrains', icon: 'ni-building text-blue', class: '' },
        { path: '/admin-reservations', title: 'Gérer Réservations', icon: 'ni-calendar-grid-58 text-orange', class: '' },
        { path: '/users', title: 'Gérer Utilisateurs', icon: 'ni-single-02 text-yellow', class: '' },
      ];
    } else if (role === 'ADHERENT') {
      routes = [
        { path: '/dashboard', title: 'Tableau de bord', icon: 'ni-tv-2 text-primary', class: '' },
        { path: '/book', title: 'Réserver un Terrain', icon: 'ni-cart text-green', class: '' },
        { path: '/my-reservations', title: 'Mes Réservations', icon: 'ni-bullet-list-67 text-red', class: '' },
      ];
    } else {
      // Public / Not Logged In
      routes = [
        { path: '/login', title: 'Se connecter', icon: 'ni-key-25 text-info', class: '' },
        { path: '/register', title: 'Créer un compte', icon: 'ni-circle-08 text-pink', class: '' }
      ];
    }
    this.menuItems = routes;
  }

  getUserName() {
    return this.authService.getUserName();
  }

  logout() {
    this.authService.logout();
  }
}
