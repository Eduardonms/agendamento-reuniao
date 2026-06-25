import { Injectable, signal } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Router } from '@angular/router';
import { tap } from 'rxjs/operators';
import { environment } from '../../environments/environment';
import { AuthResponse, AuthUser, LoginRequest, RegisterRequest } from '../models/auth.model';

const TOKEN_KEY = 'agendamento_token';
const USER_KEY = 'agendamento_user';

@Injectable({ providedIn: 'root' })
export class AuthService {
  readonly user = signal<AuthUser | null>(this.loadUser());

  constructor(
    private readonly http: HttpClient,
    private readonly router: Router
  ) {}

  login(request: LoginRequest) {
    return this.http.post<AuthResponse>(`${environment.apiUrl}/api/auth/login`, request).pipe(
      tap(response => this.persistSession(response))
    );
  }

  register(request: RegisterRequest) {
    return this.http.post<AuthResponse>(`${environment.apiUrl}/api/auth/register`, request).pipe(
      tap(response => this.persistSession(response))
    );
  }

  logout(): void {
    localStorage.removeItem(TOKEN_KEY);
    localStorage.removeItem(USER_KEY);
    this.user.set(null);
    this.router.navigate(['/login']);
  }

  getToken(): string | null {
    return localStorage.getItem(TOKEN_KEY);
  }

  isAuthenticated(): boolean {
    return !!this.getToken();
  }

  private persistSession(response: AuthResponse): void {
    localStorage.setItem(TOKEN_KEY, response.token);
    const authUser: AuthUser = { nome: response.nome, email: response.email };
    localStorage.setItem(USER_KEY, JSON.stringify(authUser));
    this.user.set(authUser);
  }

  private loadUser(): AuthUser | null {
    const raw = localStorage.getItem(USER_KEY);
    if (!raw) {
      return null;
    }
    try {
      return JSON.parse(raw) as AuthUser;
    } catch {
      return null;
    }
  }
}
