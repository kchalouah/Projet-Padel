import { Component, OnInit } from '@angular/core';
import { AuthService } from '../../services/auth.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.scss']
})
export class RegisterComponent implements OnInit {
  user = { nom: '', prenom: '', email: '', telephone: '' };
  message = '';

  constructor(private authService: AuthService, private router: Router) { }

  ngOnInit() {
  }

  register() {
    this.authService.register(this.user).subscribe({
      next: () => {
        this.message = 'Inscription réussie ! Vérifiez votre email pour le mot de passe.';
      },
      error: (err) => {
        this.message = 'Erreur lors de l\'inscription.';
      }
    });
  }
}
