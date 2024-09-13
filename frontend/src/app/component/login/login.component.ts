import { CommonModule } from '@angular/common';
import { Component, EventEmitter, Output } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { Router } from '@angular/router';
import { AuthenticationService } from '../../services/authentication.service';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [FormsModule, CommonModule],
  templateUrl: './login.component.html',
  styleUrl: './login.component.css'
})
export class LoginComponent {

  constructor(private authService: AuthenticationService,
    private router: Router) {}

	active: string = 'login';
  firstName: string = '';
  lastName: string = '';
  login: string = '';
  password: string = '';

  onLogin() {
    this.authService.loginMethod(this.login, this.password).subscribe({
      next: () => {
        this.router.navigate(['api/boards', this.login]);
      },
      error: (error) => {
        alert('Login failed!');
      }
    });
  }

  onRegister() {
    this.authService.register(this.firstName, this.lastName, this.login, this.password).subscribe({
      next: () => {
        alert('Registration success!');
        this.active = 'login';
        this.firstName= '';
        this.lastName= '';
        this.login= '';
        this.password= '';
        this.router.navigate(['api/login']);
      },
      error: (error) => {
        alert('Registration failed!');
      }
    });
  }

	onLoginTab() {
		this.active = 'login';
	}

	onRegisterTab() {
		this.active = 'register';
	}
}
