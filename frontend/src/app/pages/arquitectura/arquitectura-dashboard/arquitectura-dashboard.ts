import { Component, OnInit } from '@angular/core';
import { AuthService } from '../../../services/auth.service';
import { SolicitudService } from '../../../services/solicitud.service';
import { CommonModule, JsonPipe } from '@angular/common';
import { environment } from '../../../../environments/environments';

@Component({
  selector: 'app-arquitectura-dashboard',
  imports: [JsonPipe, CommonModule],
  templateUrl: './arquitectura-dashboard.html',
  styleUrl: './arquitectura-dashboard.css',
})
export class ArquitecturaDashboard implements OnInit {
  usuario: any = null;
  solicitudes: any[] = [];
  estadoFiltro: string = 'PENDIENTE';

  constructor(
    private authService: AuthService,
    private solicitudService: SolicitudService
  ) {}

  async ngOnInit() {
    await this.cargarUsuario();
    await this.cargarSolicitudes();
  }

  async cargarUsuario() {
    if (!this.authService.isSignedIn()) return;
    const token = await this.authService.getToken();
    const response = await fetch(`${environment.apiBaseUrl}/usuario`, {
      headers: { Authorization: `Bearer ${token}` },
    });
    this.usuario = await response.json();
  }

  async cargarSolicitudes() {
    this.solicitudes = await this.solicitudService.listarSolicitudes(this.estadoFiltro);
  }

  async cambiarEstado(id: number, nuevoEstado: string) {
    await this.solicitudService.actualizarEstado(id, nuevoEstado, this.usuario?.nombre || 'Arquitectura');
    await this.cargarSolicitudes(); // recarga después del cambio
  }
}
