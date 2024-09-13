import { Component, Input } from '@angular/core';
import { AuthenticationService } from '../../services/authentication.service';
import { Router } from '@angular/router';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-header',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './header.component.html',
  styleUrl: './header.component.css'
})
export class HeaderComponent {
  
  constructor(public authService: AuthenticationService, private router: Router) {}

  logout() {
    this.authService.setLogout();
    this.router.navigate(['api/login']);
  }

  navigateToLogin() {
    this.router.navigate(['api/login']);
  }

  navigateToBoards() {
    this.router.navigate(['api/boards', this.authService.getUsername]);
  }
}
