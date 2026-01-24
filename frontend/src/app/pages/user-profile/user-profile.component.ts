import { Component, OnInit } from '@angular/core';
import { AuthService } from '../../services/auth.service';

@Component({
  selector: 'app-user-profile',
  templateUrl: './user-profile.component.html',
  styleUrls: ['./user-profile.component.scss']
})
export class UserProfileComponent implements OnInit {

  public passwordForm = {
    currentPassword: '',
    newPassword: '',
    confirmPassword: ''
  };

  public successMessage: string = '';
  public errorMessage: string = '';

  constructor(private authService: AuthService) { }

  ngOnInit() {
  }

  getUserName() {
    return this.authService.getUserName();
  }

  getUserEmail() {
    // We could store email in localStorage too, or just parsing from name isn't possible.
    // However, we typically have the email in localStorage if we want it here.
    return localStorage.getItem('email') || 'utilisateur@padel.com';
  }

  changePassword() {
    if (this.passwordForm.newPassword !== this.passwordForm.confirmPassword) {
      this.errorMessage = 'Les mots de passe ne correspondent pas';
      return;
    }

    this.authService.changePassword({
      currentPassword: this.passwordForm.currentPassword,
      newPassword: this.passwordForm.newPassword
    }).subscribe(
      response => {
        this.successMessage = 'Mot de passe mis à jour avec succès';
        this.errorMessage = '';
        this.passwordForm = { currentPassword: '', newPassword: '', confirmPassword: '' };
      },
      error => {
        this.errorMessage = error.error?.message || 'Une erreur est survenue';
        this.successMessage = '';
      }
    );
  }
}
