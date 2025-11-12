import { Component, OnInit } from '@angular/core';
import { AuthService } from '../../../services/auth.service';
import { SolicitudService } from '../../../services/solicitud.service';
import { OrganizationService, Organization } from '../../../services/organization.service';
import { CommonModule } from '@angular/common';
import { environment } from '../../../../environments/environments';
import { firstValueFrom } from 'rxjs';

@Component({
  selector: 'app-arquitectura-dashboard',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './arquitectura-dashboard.html',
  styleUrls: ['./arquitectura-dashboard.css'],
})
export class ArquitecturaDashboard implements OnInit {
  usuario: any = null;
  token: string = '';
  
  // Para solicitudes (rama Servicio-de-correo-automatico)
  solicitudes: any[] = [];
  estadoFiltro: string = 'PENDIENTE';
  
  // Para organizaciones (rama develop)
  organizations: Organization[] = [];
  selectAll: boolean = false;
  selectedIds: number[] = [];

  constructor(
    private authService: AuthService,
    private solicitudService: SolicitudService,
    private organizationService: OrganizationService
  ) {}

  async ngOnInit() {
    // Verificar sesión activa
    if (!this.authService.isSignedIn()) return;

    // Obtener token
    this.token = (await this.authService.getToken()) || '';
    if (!this.token) {
      console.error('No se obtuvo un token válido.');
      return;
    }

    // Cargar datos en paralelo
    await Promise.all([
      this.cargarUsuario(),
      this.cargarSolicitudes(),
      this.cargarOrganizations()
    ]);
  }

  // 🔹 Obtener usuario actual
  async cargarUsuario() {
    try {
      const response = await fetch(`${environment.apiBaseUrl}/usuario`, {
        headers: { 
          Authorization: `Bearer ${this.token}` 
        },
        credentials: 'include',
      });
      if (!response.ok) throw new Error('Error al obtener usuario');
      this.usuario = await response.json();
    } catch (error) {
      console.error('Error al cargar usuario:', error);
    }
  }

  // 🔹 Obtener solicitudes pendientes (Servicio-de-correo-automatico)
  async cargarSolicitudes() {
    try {
      this.solicitudes = await this.solicitudService.listarSolicitudes(this.estadoFiltro);
    } catch (error) {
      console.error('Error al cargar solicitudes:', error);
    }
  }

  // 🔹 Cambiar estado de solicitud
  async cambiarEstado(id: number, nuevoEstado: string) {
    try {
      await this.solicitudService.actualizarEstado(
        id, 
        nuevoEstado, 
        this.usuario?.nombre || 'Arquitectura'
      );
      await this.cargarSolicitudes(); // Recargar después del cambio
      alert(`Solicitud ${nuevoEstado.toLowerCase()} correctamente`);
    } catch (error) {
      console.error('Error al cambiar estado de solicitud:', error);
      alert('No se pudo cambiar el estado de la solicitud');
    }
  }

  // 🔹 Obtener todas las organizaciones (develop)
  async cargarOrganizations() {
    try {
      this.organizations = await firstValueFrom(
        this.organizationService.getAll(this.token)
      );
    } catch (error) {
      console.error('Error al obtener organizaciones:', error);
    }
  }

  // 📊 Getters para estadísticas
  get totalOrganizaciones(): number {
    return this.organizations.length;
  }

  get activas(): number {
    return this.organizations.filter(o => o.activo).length;
  }

  get inactivas(): number {
    return this.organizations.filter(o => !o.activo).length;
  }

  // 🔹 Cambiar estado activo/inactivo de organización
  async toggleActivo(org: Organization) {
    try {
      const updated: Organization = await firstValueFrom(
        this.organizationService.toggleEstado(org.id, !org.activo, this.token)
      );
      org.activo = updated.activo;
    } catch (error) {
      console.error('Error al actualizar estado:', error);
      alert('No se pudo actualizar el estado de la organización');
    }
  }

  // 🔹 Eliminar organización
  async eliminarOrganizacion(id: number) {
    const confirmar = confirm('¿Seguro que deseas eliminar esta organización?');
    if (!confirmar) return;

    try {
      await firstValueFrom(this.organizationService.delete(id, this.token));
      alert('Organización eliminada correctamente');
      this.organizations = this.organizations.filter(org => org.id !== id);
    } catch (error) {
      console.error('Error al eliminar organización:', error);
      alert('No se pudo eliminar la organización');
    }
  }

  // 🔹 Seleccionar todas las organizaciones
  toggleSelectAll(event: any) {
    this.selectAll = event.target.checked;
    this.selectedIds = this.selectAll 
      ? this.organizations.map(org => org.id) 
      : [];
  }

  // 🔹 Seleccionar una organización individual
  toggleSelect(orgId: number, event: any) {
    if (event.target.checked) {
      this.selectedIds.push(orgId);
    } else {
      this.selectedIds = this.selectedIds.filter(id => id !== orgId);
    }
    this.selectAll = this.selectedIds.length === this.organizations.length;
  }

  // 🔹 Acción masiva: cambiar estado de seleccionadas
  async cambiarEstadoSeleccionadas() {
    if (this.selectedIds.length === 0) return;
    const confirmar = confirm(`¿Cambiar estado de ${this.selectedIds.length} organización(es)?`);
    if (!confirmar) return;

    for (const id of this.selectedIds) {
      const org = this.organizations.find(o => o.id === id);
      if (org) await this.toggleActivo(org);
    }

    this.selectedIds = [];
    this.selectAll = false;
  }

  // 🔹 Acción masiva: eliminar seleccionadas
  async eliminarSeleccionadas() {
    if (this.selectedIds.length === 0) return;
    const confirmar = confirm(`¿Eliminar ${this.selectedIds.length} organización(es)?`);
    if (!confirmar) return;

    for (const id of this.selectedIds) {
      await this.eliminarOrganizacion(id);
    }

    this.selectedIds = [];
    this.selectAll = false;
  }
}