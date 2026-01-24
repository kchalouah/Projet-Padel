import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { tap } from 'rxjs/operators';
import { environment } from 'src/environments/environment';

@Injectable({
    providedIn: 'root'
})
export class AuthService {
    private apiUrl = environment.apiUrl + '/auth';

    constructor(private http: HttpClient) { }

    login(credentials: any): Observable<any> {
        return this.http.post(`${this.apiUrl}/login`, credentials).pipe(
            tap((response: any) => {
                if (response && response.token) {
                    this.saveToken(response.token);
                    this.saveUserRole(response.role);
                    localStorage.setItem('nom', response.nom);
                    localStorage.setItem('prenom', response.prenom);
                }
            })
        );
    }

    getUserName(): string {
        const nom = localStorage.getItem('nom');
        const prenom = localStorage.getItem('prenom');

        if (!nom || nom === 'undefined' || !prenom || prenom === 'undefined') {
            return 'Utilisateur';
        }
        return nom + ' ' + prenom;
    }

    register(user: any): Observable<any> {
        return this.http.post(`${this.apiUrl}/register`, user);
    }

    saveToken(token: string): void {
        localStorage.setItem('token', token);
    }

    getToken(): string | null {
        return localStorage.getItem('token');
    }

    saveUserRole(role: string): void {
        localStorage.setItem('role', role);
    }

    getUserRole(): string | null {
        return localStorage.getItem('role');
    }

    isLoggedIn(): boolean {
        return !!this.getToken();
    }

    changePassword(payload: any): Observable<any> {
        return this.http.post(`${this.apiUrl}/change-password`, payload);
    }

    logout(): void {
        localStorage.removeItem('token');
        localStorage.removeItem('role');
        localStorage.removeItem('nom');
        localStorage.removeItem('prenom');
        window.location.href = '/';
    }
}
