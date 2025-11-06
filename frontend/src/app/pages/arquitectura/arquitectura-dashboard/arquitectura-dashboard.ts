import { Component, OnInit } from '@angular/core';
import { AuthService } from '../../../services/auth.service';
import { CommonModule, JsonPipe } from '@angular/common';
import { OrganizationService, Organization } from '../../../services/organization.service';
import { firstValueFrom } from 'rxjs';

@Component({
  selector: 'app-arquitectura-dashboard',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './arquitectura-dashboard.html',
  styleUrls: ['./arquitectura-dashboard.css'],
})
export class ArquitecturaDashboard implements OnInit {
  token: string = '';
  usuario: any = null;
  organizations: Organization[] = [];
  sinOrganizaciones = null
  selectAll: boolean = false;
  selectedIds: number[] = [];


  constructor(
    private authService: AuthService,
    private organizationService: OrganizationService
  ) {}

  async ngOnInit() {
    // 1️Verificar sesión activa
    if (!this.authService.isSignedIn()) return;

    // 2Obtener token de sesión
    this.token = (await this.authService.getToken()) || '';
    if (!this.token) {
      console.error('No se obtuvo un token válido.');
      return;
    }

    // Cargar usuario y organizaciones
    await this.cargarUsuario();
    await this.cargarOrganizations();
  }

  // 🔹 Obtener usuario actual
  async cargarUsuario() {
    try {
      // Aquí puedes usar el endpoint de usuario directamente con fetch o un servicio
      const res = await fetch(`/usuario`, {
        headers: { Authorization: `Bearer ${this.token}` },
        credentials: 'include',
      });
      if (!res.ok) throw new Error('Error al obtener usuario');
      this.usuario = await res.json();
    } catch (error) {
      console.error('Error al obtener usuario:', error);
    }
  }

  // 🔹 Obtener todas las organizaciones usando OrganizationService
  async cargarOrganizations() {
    try {
      this.organizations = await firstValueFrom(
        this.organizationService.getAll(this.token)
      );
    } catch (error) {
      console.error('Error al obtener organizaciones:', error);
    }
  }

  get totalOrganizaciones(): number {
    return this.organizations.length;
  }

  get activas(): number {
    return this.organizations.filter(o => o.activo).length;
  }

  get inactivas(): number {
    return this.organizations.filter(o => !o.activo).length;
  }


  // Cambiar estado activo/inactivo de la organización usando OrganizationService
  async toggleActivo(org: Organization) {
    try {
      const updated = await firstValueFrom(
        this.organizationService.toggleEstado(org.id, !org.activo, this.token)
      );
      org.activo = updated.activo;
    } catch (error) {
      console.error('Error al actualizar estado:', error);
      alert('No se pudo actualizar el estado de la organización');
    }
  }

  // 🔹 Eliminar organización usando OrganizationService
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

    toggleSelectAll(event: any) {
    this.selectAll = event.target.checked;

    if (this.selectAll) {
      this.selectedIds = this.organizations.map(org => org.id);
    } else {
      this.selectedIds = [];
    }
  }

  toggleSelect(orgId: number, event: any) {
    if (event.target.checked) {
      this.selectedIds.push(orgId);
    } else {
      this.selectedIds = this.selectedIds.filter(id => id !== orgId);
    }

    // actualiza el checkbox principal si todos están seleccionados
    this.selectAll = this.selectedIds.length === this.organizations.length;
  }

  // 🆕 Acción masiva: cambiar estado
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

  // 🆕 Acción masiva: eliminar seleccionadas
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
