import { Routes } from '@angular/router';
import { SignUpPage } from './pages/auth/sign-up-page/sign-up-page';
import { SignInPage } from './pages/auth/sign-in-page/sign-in-page';
import { ArquitecturaLayout } from './layouts/arquitectura-layout/arquitectura-layout';
import { AuthGuard } from './services/auth.guard';
import { ArquitecturaDashboard } from './pages/arquitectura/arquitectura-dashboard/arquitectura-dashboard';
import { AuditoriaDashboard } from './pages/auditoria/auditoria-dashboard/auditoria-dashboard';
import { TesoreriaDashboard } from './pages/tesoreria/tesoreria-dashboard/tesoreria-dashboard';
import { AuditoriaLayout } from './layouts/auditoria-layout/auditoria-layout';
import { TesoreriaLayout } from './layouts/tesoreria-layout/tesoreria-layout';

export const routes: Routes = [
  { path: 'singup', component: SignUpPage },
  { path: 'login', component: SignInPage },
  {
    path: 'arquitectura',
    component: ArquitecturaLayout,
    canActivate: [AuthGuard],
    data: { role: 'arquitectura' },
    children: [
      {
        path: 'dashboard',
        component: ArquitecturaDashboard,
      },
    ],
  },
  {
    path: 'auditor',
    component: AuditorLayout,
    canActivate: [AuthGuard],
    data: { role: 'auditor' },
    children: [
      {
        path: 'dashboard',
        component: AuditorDashboard,
      },
    ],
  },
  {
    path: 'tesoreria',
    component: TesoreriaLayout,
    canActivate: [AuthGuard],
    data: { role: 'tesoreria' },
    children: [
      {
        path: 'dashboard',
        component: TesoreriaDashboard,
      },
    ],
  },
];
