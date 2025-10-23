import { Component, OnInit } from '@angular/core';
import { AuthService } from '../../../services/auth.service';
import axios from 'axios';
import { CommonModule, JsonPipe } from '@angular/common';
import {environment} from '../../../../environments/environments';

@Component({
  selector: 'app-arquitectura-dashboard',
  standalone: true,
  imports: [JsonPipe, CommonModule],
  templateUrl: './arquitectura-dashboard.html',
  styleUrl: './arquitectura-dashboard.css',
})
export class ArquitecturaDashboard implements OnInit {
  token: string = '';
  usuario: any = null;
  plazasPendientes: any[] = [];


  constructor(private authService: AuthService) {}

  async ngOnInit() {
    // Espera a que Clerk esté inicializado y el usuario esté autenticado
    if (!this.authService.isSignedIn()) {
      console.warn('Usuario no autenticado. Redirigiendo a login...');
      // Aquí podrías redirigir al login si lo deseas
      return;
    }

    this.token = (await this.authService.getToken()) || '';
    console.log('Token obtenido:', this.token);

    if (!this.token) {
      console.error('No se obtuvo un token válido.');
      return;
    }

    await this.cargarUsuario();
    await this.cargarPlazasPendientes();
  }

  async cargarUsuario() {
    try {
      const response = await fetch(`${environment.apiBaseUrl}/usuario`, {
        headers: { Authorization: `Bearer ${this.token}` },
      });
      if (!response.ok) throw new Error('Error al obtener usuario');
      this.usuario = await response.json();
    } catch (error) {
      console.error('Error al obtener usuario:', error);
    }
  }

  async cargarPlazasPendientes() {
  try {
    const response = await fetch(`${environment.apiBaseUrl}/admin/plazas/pendientes`, {
      headers: { Authorization: `Bearer ${this.token}` },
    });
    if (!response.ok) throw new Error('Error al obtener plazas');
    this.plazasPendientes = await response.json();
  } catch (error) {
    console.error('Error al obtener plazas pendientes:', error);
  }
}

async aprobarPlaza(plazaId: number) {
  const confirmar = confirm('¿Seguro que deseas aprobar esta plaza?');
  if (!confirmar) return;

  try {
    // DTO mínimo requerido por el backend
    const dto = {};  

    const response = await fetch(`${environment.apiBaseUrl}/admin/plazas/${plazaId}/aprobar`, {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
        Authorization: `Bearer ${this.token}`,
      },
      body: JSON.stringify(dto),
    });

    if (!response.ok) {
      const errorText = await response.text();
      throw new Error(`Error al aprobar plaza: ${errorText}`);
    }

    alert('Plaza aprobada correctamente.');
    await this.cargarPlazasPendientes();
  } catch (error) {
    console.error(error);
    alert('Ocurrió un error al aprobar la plaza.');
  }
}

  async rechazarPlaza(plazaId: number) {
    const motivo = prompt('Ingresa el motivo del rechazo:');
    if (!motivo) return;

    try {
      const dto = { motivo };  // DTO mínimo requerido por el backend

      const response = await fetch(`${environment.apiBaseUrl}/admin/plazas/${plazaId}/rechazar`, {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
          Authorization: `Bearer ${this.token}`,
        },
        body: JSON.stringify(dto),
      });

      if (!response.ok) {
        const errorText = await response.text();
        throw new Error(`Error al rechazar plaza: ${errorText}`);
      }

      alert('Plaza rechazada correctamente.');
      await this.cargarPlazasPendientes();
    } catch (error) {
      console.error(error);
      alert('Ocurrió un error al rechazar la plaza.');
    }
  }

}
