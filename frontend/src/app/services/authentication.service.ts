import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, tap } from 'rxjs';
import { LoginResponse } from '../interfaces/loginResponse.interface';

@Injectable({
  providedIn: 'root'
})
export class AuthenticationService {

  private apiUrl = 'http://localhost:8080/api';
  private loggedIn = false;
  private username = '';

  constructor(private http: HttpClient) {}

  loginMethod(username: string, password: string): Observable<LoginResponse> {
    return this.http.post<LoginResponse>(`${this.apiUrl}/login`, { username, password }).pipe(tap((data) => {
        this.setAuthToken(data.token);
        this.loggedIn = true;
        this.username = data.username;
      })
    );
  }
  
  register(firstname: string, lastname: string, username: string, password: string): Observable<LoginResponse> {
    return this.http.post<LoginResponse>(`${this.apiUrl}/register`, { firstname, lastname, username, password });
  } 

  setAuthToken(token: string | null) { 
    if (token != null) {
      window.localStorage.setItem('auth_token', token);
    } else {
      window.localStorage.removeItem('auth_token');
      this.username = ''
    }
  }

  getAuthToken(): string | null { 
    return window.localStorage.getItem('auth_token');
  }

  setLogin() {
    this.loggedIn = true;
  }

  setLogout() {
    this.loggedIn = false;
  }

  isLoggedIn(): boolean {
    return this.loggedIn;
  }
  
  getUsername(): string {
    return this.username;
  }
  
}