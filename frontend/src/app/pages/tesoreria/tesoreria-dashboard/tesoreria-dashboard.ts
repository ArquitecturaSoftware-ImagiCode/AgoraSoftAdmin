import { Component, OnInit } from '@angular/core';
import { AuthService } from '../../../services/auth.service';
import { CommonModule } from '@angular/common';
import { RouterLink } from '@angular/router';
import {environment} from '../../../../environments/environments';

@Component({
  selector: 'app-tesoreria-dashboard',
  imports: [CommonModule, RouterLink],
  templateUrl: './tesoreria-dashboard.html',
  styleUrls: ['./tesoreria-dashboard.css']
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
      
      const userId = await this.authService.getUserId();
      console.log('User ID obtenido:', userId);
      
      const response = await fetch(`${environment.apiBaseUrl}/usuario`, {
        method: 'GET',
        headers: { Authorization: `Bearer ${this.token}` },
      });
      
      console.log('Response status:', response.status);
      console.log('Response ok:', response.ok);
      
      if (!response.ok) {
        const errorText = await response.text();
        console.error('Error response:', errorText);
        throw new Error('Error HTTP: ' + response.status + ' - ' + errorText);
      }
      
      const data = await response.json();
      console.log('Datos recibidos:', data);
      
      // Verificar si hay un error en la respuesta
      if (data.error) {
        console.warn('El backend devolvió un error:', data.error);
        // Si el usuario no existe, intentar obtenerlo desde Clerk directamente
        if (data.error.includes('no encontrado') || data.error.includes('No Clerk userId')) {
          // Intentar obtener datos de Clerk directamente
          const clerkInstance = this.authService.getClerkInstance();
          const clerkUser = await clerkInstance.user;
          console.log('Datos de Clerk:', clerkUser);
          
          if (clerkUser) {
            const emailAddress = clerkUser.emailAddresses?.[0]?.emailAddress || 
                               clerkUser.primaryEmailAddress?.emailAddress || 
                               '';
            
            this.usuario = {
              id: clerkUser.id || userId,
              nombre: clerkUser.firstName || '',
              apellido: clerkUser.lastName || '',
              correo: emailAddress,
              rol: (clerkUser.unsafeMetadata?.['role'] as string) || 'tesorería'
            };
            console.log('Usuario construido desde Clerk:', this.usuario);
          } else {
            throw new Error(data.error);
          }
        } else {
          throw new Error(data.error);
        }
      } else {
        this.usuario = data;
        console.log('Usuario recibido:', this.usuario);
        console.log('Usuario nombre:', this.usuario?.nombre);
        console.log('Usuario correo:', this.usuario?.correo);
      }
      
      this.cargandoUsuario = false;
      
    } catch (error) {
      console.error('Error al obtener usuario:', error);
      this.errorCarga = true;
      this.cargandoUsuario = false;
    }
  }

  getNombreCompleto(): string {
    if (!this.usuario) return '';
    const nombre = this.usuario.nombre || '';
    const apellido = this.usuario.apellido || '';
    return `${nombre} ${apellido}`.trim() || 'No disponible';
  }
}
