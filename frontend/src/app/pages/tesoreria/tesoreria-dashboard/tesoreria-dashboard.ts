import { Component, OnInit } from '@angular/core';
import { AuthService } from '../../../services/auth.service';
import { CommonModule } from '@angular/common';
import { environment } from '../../../../environments/environments';

@Component({
  selector: 'app-tesoreria-dashboard',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './tesoreria-dashboard.html',
  styleUrls: ['./tesoreria-dashboard.css']
})
export class TesoreriaDashboard implements OnInit {
  token: string = '';
  usuario: any = null;
  cargando = true;
  error = '';

  constructor(private authService: AuthService) {}

  async ngOnInit() {
    console.log('[TesoreriaDashboard] ngOnInit ejecutado');

    if (!this.authService.isSignedIn()) {
      this.error = 'Debe iniciar sesión.';
      this.cargando = false;
      return;
    }

    this.token = (await this.authService.getToken()) || '';
    if (!this.token) {
      this.error = 'No se pudo obtener el token de autenticación.';
      this.cargando = false;
      return;
    }

    console.log('[TesoreriaDashboard] Token obtenido:', this.token);

    try {
      const userId = await this.authService.getUserId();
      if (!userId) throw new Error('No se pudo obtener el ID del usuario.');

      const response = await fetch(`${environment.apiBaseUrl}/usuarios/${userId}`, {
        method: 'GET',
        headers: { Authorization: `Bearer ${this.token}` },
      });

      if (!response.ok) throw new Error('Error HTTP: ' + response.status);

      this.usuario = await response.json();
      console.log('[TesoreriaDashboard] Usuario cargado:', this.usuario);
    } catch (error) {
      console.error('[TesoreriaDashboard] Error al obtener usuario:', error);
      this.error = 'No se pudo cargar la información del usuario.';
    } finally {
      this.cargando = false;
    }
  }
}
