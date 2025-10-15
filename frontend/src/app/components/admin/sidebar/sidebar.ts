import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Router, RouterModule } from '@angular/router';
import { EmpleadoService } from '../../services/empleado.service';
import { Empleado } from '../../models/empleado.model';
import { catchError, of } from 'rxjs';
import { Inject } from '@angular/core';

interface MenuItem {
  icon: string;
  label: string;
  route: string;
  active: boolean;
  permission?: string;
  badge?: number;
}

@Component({
  selector: 'app-sidebar',
  standalone: true,
  imports: [CommonModule, RouterModule],
  templateUrl: './sidebar.html',
  styleUrls: ['./sidebar.css']
})
export class SidebarComponent implements OnInit {
  empleadoActual: Empleado | null = null;
  menuItems: MenuItem[] = [
    { icon: 'dashboard', label: 'Dashboard', route: '/admin/dashboard', active: true },
    { icon: 'people', label: 'Gestión de Empleados', route: '/admin/empleados', active: false, permission: 'MANAGE_EMPLOYEES' },
    { icon: 'store', label: 'Gestión de Plazas', route: '/admin/plazas', active: false, permission: 'MANAGE_PLAZAS' },
    { icon: 'view_module', label: 'Gestión de Módulos', route: '/admin/modulos', active: false, permission: 'MANAGE_MODULES' },
    { icon: 'pending_actions', label: 'Plazas Pendientes', route: '/admin/plazas/pendientes', active: false, permission: 'APPROVE_PLAZAS', badge: 0 }
  ];
  constructor(private router: Router, @Inject(EmpleadoService) private empleadoService: EmpleadoService) {}


  ngOnInit(): void {
    this.cargarEmpleadoActual();
  }

  private cargarEmpleadoActual(): void {
    this.empleadoService.obtenerEmpleadoActual()
      .pipe(
        catchError(err => {
          console.warn('No se pudo cargar empleado actual:', err);
          return of(null);
        })
      )
      .subscribe((empleado: Empleado | null) => {
        if (empleado) {
          this.empleadoActual = empleado;
          this.filtrarMenuPorPermisos();
        }
      });
  }

  private filtrarMenuPorPermisos(): void {
    if (!this.empleadoActual) return;

    // Mantiene solo items permitidos por rol
    this.menuItems = this.menuItems.filter(item => {
      if (!item.permission) return true;
      return this.tienePermiso(item.permission);
    });
  }

  tienePermiso(permiso: string): boolean {
    if (!this.empleadoActual || !this.empleadoActual.rol) return false;

    const mapPermisos: Record<string, string[]> = {
      SUPER_ADMIN: ['FULL_ACCESS', 'MANAGE_EMPLOYEES', 'MANAGE_PLAZAS', 'MANAGE_MODULES', 'APPROVE_PLAZAS'],
      ADMIN: ['MANAGE_PLAZAS', 'APPROVE_PLAZAS', 'VIEW_EMPLOYEES'],
      SOPORTE: ['VIEW_PLAZAS']
    };

    const permisosRol = mapPermisos[this.empleadoActual.rol as keyof typeof mapPermisos] ?? [];
    return permisosRol.includes(permiso) || permisosRol.includes('FULL_ACCESS');
  }

  navegarA(route: string): void {
    this.menuItems.forEach(i => i.active = (i.route === route));
    this.router.navigate([route]);
  }

  cerrarSesion(): void {
    localStorage.removeItem('clerk_token');
    // opcional: limpiar otros datos
    this.router.navigate(['/login']);
  }
}
