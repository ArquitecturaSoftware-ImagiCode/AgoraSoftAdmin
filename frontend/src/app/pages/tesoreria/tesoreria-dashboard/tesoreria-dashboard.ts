import { Component, OnInit } from '@angular/core';
import { AuthService } from '../../../services/auth.service';
import { CommonModule } from '@angular/common';
import { RouterLink } from '@angular/router';
import {environment} from '../../../../environments/environments';

@Component({
  selector: 'app-tesoreria-dashboard',
  imports: [CommonModule, RouterLink],
  templateUrl: './tesoreria-dashboard.html',
  styleUrl: './tesoreria-dashboard.css'
})
export class TesoreriaDashboard implements OnInit {
  token: string = '';
  usuario: any = null;
  cargandoUsuario: boolean = true;
  errorCarga: boolean = false;

  constructor(private authService: AuthService) {}

  async ngOnInit() {
    this.cargandoUsuario = true;
    this.errorCarga = false;
    
    try {
      // Espera a que Clerk esté inicializado y el usuario esté autenticado
      if (!this.authService.isSignedIn()) {
        console.warn('Usuario no autenticado. Redirigiendo a login...');
        this.errorCarga = true;
        this.cargandoUsuario = false;
        return;
      }
      
      this.token = (await this.authService.getToken()) || '';
      console.log('Token obtenido:', this.token);
      
      if (!this.token) {
        console.error('No se obtuvo un token válido.');
        this.errorCarga = true;
        this.cargandoUsuario = false;
        return;
      }
      
      const response = await fetch(`${environment.apiBaseUrl}/usuario`, {
        method: 'GET',
        headers: {
          Authorization: `Bearer ${this.token.toString()}`,
        },
        credentials: 'include', // 🔹 si usas cookies en Clerk
      });
      
      if (!response.ok) {
        throw new Error('Error HTTP: ' + response.status);
      }
      
      this.usuario = await response.json();
      this.cargandoUsuario = false;
      
    } catch (error) {
      console.error('Error al obtener usuario:', error);
      this.errorCarga = true;
      this.cargandoUsuario = false;
    }
  }
}
