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
        path: 'empleados',
        children: [
          { path: 'registro', component: RegistroEmpleadoComponent },
          { path: 'lista', component: ListaEmpleadosComponent },
        ],
      },
       { path: '', redirectTo: 'dashboard', pathMatch: 'full' }

    ],
  },
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
       { path: '', redirectTo: 'dashboard', pathMatch: 'full' }
    ],
  },
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
       { path: '', redirectTo: 'dashboard', pathMatch: 'full' }
    ],
  },
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
       { path: '', redirectTo: 'dashboard', pathMatch: 'full' }
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
       { path: '', redirectTo: 'dashboard', pathMatch: 'full' }
    ],
  },
/*
{
    path: 'admin',
    // admin layout is a standalone component (AdminLayoutComponent) - lazy load it
    loadComponent: () => import('./admin/admin-layout.component').then(m => m.AdminLayoutComponent),
    canActivate: [AuthGuard],
    data: { role: 'admin' },
    children: [
      { path: '', redirectTo: 'dashboard', pathMatch: 'full' },
      {
        path: 'dashboard',
        loadComponent: () => import('./admin/dashboard/dashboard.component').then(m => m.DashboardComponent)
      },

      // Empleados
      {
        path: 'empleados',
        loadComponent: () => import('./admin/empleados/empleados-list.component').then(m => m.EmpleadosListComponent)
      },
      {
        path: 'empleados/nuevo',
        loadComponent: () => import('./admin/empleados/empleado-form.component').then(m => m.EmpleadoFormComponent)
      },
      {
        path: 'empleados/:id',
        loadComponent: () => import('./admin/empleados/empleado-detail.component').then(m => m.EmpleadoDetailComponent)
      },
      {
        path: 'empleados/:id/editar',
        loadComponent: () => import('./admin/empleados/empleado-form.component').then(m => m.EmpleadoFormComponent)
      },

      // Plazas
      {
        path: 'plazas',
        loadComponent: () => import('./admin/plazas/plazas-list.component').then(m => m.PlazasListComponent)
      },
      {
        path: 'plazas/:id',
        loadComponent: () => import('./admin/plazas/plaza-detail.component').then(m => m.PlazaDetailComponent)
      },
      {
        path: 'plazas/:id/aprobar',
        loadComponent: () => import('./admin/plazas/plaza-aprobacion.component').then(m => m.PlazaAprobacionComponent)
      },

      // Módulos
      {
        path: 'modulos',
        loadComponent: () => import('./admin/modulos/modulos-list.component').then(m => m.ModulosListComponent)
      },
      {
        path: 'modulos/nuevo',
        loadComponent: () => import('./admin/modulos/modulo-form.component').then(m => m.ModuloFormComponent)
      },
      {
        path: 'modulos/:id/editar',
        loadComponent: () => import('./admin/modulos/modulo-form.component').then(m => m.ModuloFormComponent)
      }
    ]
  },

  // default / fallback
  { path: '', redirectTo: 'login', pathMatch: 'full' },
  { path: '**', redirectTo: '' }
*/

];
