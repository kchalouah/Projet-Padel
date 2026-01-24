import { Routes } from '@angular/router';

import { DashboardComponent } from '../../pages/dashboard/dashboard.component';
import { IconsComponent } from '../../pages/icons/icons.component';
import { MapsComponent } from '../../pages/maps/maps.component';
import { UserProfileComponent } from '../../pages/user-profile/user-profile.component';
import { TablesComponent } from '../../pages/tables/tables.component';
import { TerrainsComponent } from '../../pages/terrains/terrains.component';
import { MyReservationsComponent } from '../../pages/my-reservations/my-reservations.component';
import { ReservationBookComponent } from '../../pages/reservation-book/reservation-book.component';
import { AdminReservationsComponent } from '../../pages/admin-reservations/admin-reservations.component';
import { UsersComponent } from '../../pages/users/users.component';

export const AdminLayoutRoutes: Routes = [
    { path: 'dashboard', component: DashboardComponent },
    { path: 'user-profile', component: UserProfileComponent },
    { path: 'tables', component: TablesComponent },
    { path: 'icons', component: IconsComponent },
    { path: 'maps', component: MapsComponent },
    { path: 'terrains', component: TerrainsComponent },
    { path: 'users', component: UsersComponent },
    { path: 'my-reservations', component: MyReservationsComponent },
    { path: 'book', component: ReservationBookComponent },
    { path: 'admin-reservations', component: AdminReservationsComponent }
];
