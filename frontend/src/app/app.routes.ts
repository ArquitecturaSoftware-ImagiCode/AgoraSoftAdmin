import { Routes } from '@angular/router';
import { SignUpPage } from './pages/auth/sign-up-page/sign-up-page';
import { SignInPage } from './pages/auth/sign-in-page/sign-in-page';
import { ArquitecturaLayout } from './layouts/arquitectura-layout/arquitectura-layout';
import { AuthGuard } from './services/auth.guard';
import { ArquitecturaDashboard } from './pages/arquitectura/arquitectura-dashboard/arquitectura-dashboard';
import { TesoreriaDashboard } from './pages/tesoreria/tesoreria-dashboard/tesoreria-dashboard';
import { AuditoriaDashboard } from './pages/auditoria/auditoria-dashboard/auditoria-dashboard';
import { PlaneacionDashboard } from './pages/planeacion/planeacion-dashboard/planeacion-dashboard';
import { SoporteDashboard } from './pages/soporte/soporte-dashboard/soporte-dashboard';
import { AuditoriaLayout } from './layouts/auditoria-layout/auditoria-layout';
import { TesoreriaLayout } from './layouts/tesoreria-layout/tesoreria-layout';
import { PlaneacionLayout } from './layouts/planeacion-layout/planeacion-layout';
import { SoporteLayout } from './layouts/soporte-layout/soporte-layout';
import { RegistroEmpleadoComponent } from './pages/empleados/registro-empleado/registro-empleado.component';
import { ListaEmpleadosComponent } from './pages/empleados/lista-empleados/lista-empleados.component';
import { GestionPagosPage } from './pages/tesoreria/gestion-pagos/gestion-pagos';
import { RegistroPagoComponent } from './pages/tesoreria/registro-pago/registro-pago';
import { ReportesComponent } from './pages/reportes/reportes';
import { ModulosArquitectura } from './pages/arquitectura/modulos-arquitectura/modulos-arquitectura';

export const routes: Routes = [
  { path: 'register', component: SignUpPage },
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
      {
        path: 'modulos',
        component: ModulosArquitectura
      },
      // Empleados dentro de arquitectura
      {
        path: 'empleados/registro',
        loadComponent: () =>
          import('./pages/empleados/registro-empleado/registro-empleado.component').then(
            (m) => m.RegistroEmpleadoComponent
          ),
      },
      {
        path: 'empleados/lista',
        loadComponent: () =>
          import('./pages/empleados/lista-empleados/lista-empleados.component').then(
            (m) => m.ListaEmpleadosComponent
          ),
      },
      { path: '', redirectTo: 'dashboard', pathMatch: 'full' },
    ],
  },

  // Auditoria
  {
    path: 'auditoria',
    component: AuditoriaLayout,
    canActivate: [AuthGuard],
    data: { role: 'auditoria' },
    children: [
      {
        path: 'dashboard',
        component: AuditoriaDashboard,
      },
      { path: '', redirectTo: 'dashboard', pathMatch: 'full' },
    ],
  },

  // Planeacion
  {
    path: 'planeacion',
    component: PlaneacionLayout,
    canActivate: [AuthGuard],
    data: { role: 'planeacion' },
    children: [
      {
        path: 'dashboard',
        component: PlaneacionDashboard,
      },
      { path: '', redirectTo: 'dashboard', pathMatch: 'full' },
    ],
  },

  // Soporte
  {
    path: 'soporte',
    component: SoporteLayout,
    canActivate: [AuthGuard],
    data: { role: 'soporte' },
    children: [
      {
        path: 'dashboard',
        component: SoporteDashboard,
      },
      {
        path: 'empleados',
        children: [
          { path: 'registro', component: RegistroEmpleadoComponent },
          { path: 'lista', component: ListaEmpleadosComponent },
        ],
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
      {
        path: 'gestion-pagos',
        component: GestionPagosPage,
      },
      {
        path: 'registro-pago',
        component: RegistroPagoComponent,
      },
      {
        path: 'reportes',
        component: ReportesComponent,
      },
      {
        path: 'empleados',
        children: [
          { path: 'registro', component: RegistroEmpleadoComponent },
          { path: 'lista', component: ListaEmpleadosComponent },
        ],
      },
    ],
  },

  // Default & fallback
  { path: '', redirectTo: 'login', pathMatch: 'full' },
  { path: '**', redirectTo: '' },
];