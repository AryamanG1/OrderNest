import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Router } from '@angular/router'; // ✅ Import Router

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private baseUrl = 'http://localhost:8080/api/auth';
  private tokenKey = 'token';

  // ✅ Inject HttpClient and Router
  constructor(private http: HttpClient, private router: Router) {}

  // API call to login
  login(data: { username: string; password: string }): Observable<any> {
    return this.http.post(`${this.baseUrl}/signin`, data);
  }

  // API call to sign up
  signup(data: any): Observable<any> {
    return this.http.post(`${this.baseUrl}/signup`, data);
  }

  // Save token in local storage
  setToken(token: string): void {
    localStorage.setItem(this.tokenKey, token);
  }

  // Get token from local storage
  getToken(): string | null {
    return localStorage.getItem(this.tokenKey);
  }

  // Remove token and redirect to home
  logout(): void {
    localStorage.removeItem(this.tokenKey);
    this.router.navigate(['/']); // ✅ Redirect to home
  }

  // Check login status
  isLoggedIn(): boolean {
    return !!this.getToken();
  }
}
