import { Routes } from '@angular/router';
import { authGuard, guestGuard } from './guards/auth.guard';

export const routes: Routes = [
  {
    path: '',
    pathMatch: 'full',
    redirectTo: 'agendamentos'
  },
  {
    path: 'login',
    canActivate: [guestGuard],
    loadComponent: () => import('./pages/login/login.component').then(m => m.LoginComponent)
  },
  {
    path: 'register',
    canActivate: [guestGuard],
    loadComponent: () => import('./pages/register/register.component').then(m => m.RegisterComponent)
  },
  {
    path: 'agendamentos',
    canActivate: [authGuard],
    loadComponent: () =>
      import('./pages/agendamentos/agendamento-list.component').then(m => m.AgendamentoListComponent)
  },
  {
    path: '**',
    redirectTo: 'agendamentos'
  }
];
