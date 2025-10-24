import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { UsuarioService } from '../../../services/usuario.service';
import { Usuario } from '../../../models/Usuario';

type Plaza = {
  id: number;
  nombre: string;
  estadoPago: 'pendiente' | 'pagado';
  monto: number;
  fechaLimite: string;
};

type UsuarioEmpresa = {
  id: string;
  nombre: string;
  correo: string;
  rol: string;
  apellido: string;
  organizacion: string;
};

@Component({
  selector: 'app-gestion-pagos',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './gestion-pagos.html',
  styleUrl: './gestion-pagos.css',
})
export class GestionPagosPage implements OnInit {
  plazas: Plaza[] = [
    { id: 1, nombre: 'Plaza Central', estadoPago: 'pendiente', monto: 1500000, fechaLimite: '2025-10-31' },
    { id: 2, nombre: 'Plaza Norte', estadoPago: 'pagado', monto: 980000, fechaLimite: '2025-09-30' },
    { id: 3, nombre: 'Plaza Sur', estadoPago: 'pendiente', monto: 720000, fechaLimite: '2025-11-15' },
    { id: 4, nombre: 'Plaza Occidente', estadoPago: 'pagado', monto: 1260000, fechaLimite: '2025-09-20' },
  ];

  usuarios: UsuarioEmpresa[] = [];
  cargandoUsuarios: boolean = true;

  constructor(private usuarioService: UsuarioService) {}

  ngOnInit() {
    this.cargarUsuariosContratistas();
  }

  get plazasPendientes(): Plaza[] {
    return this.plazas.filter((p) => p.estadoPago === 'pendiente');
  }

  get plazasPagadas(): Plaza[] {
    return this.plazas.filter((p) => p.estadoPago === 'pagado');
  }

  cargarUsuariosContratistas() {
    this.cargandoUsuarios = true;
    console.log('Cargando usuarios contratistas...');
    this.usuarioService.getUsuariosPorOrganizacion('contratista').subscribe({
      next: (usuarios: Usuario[]) => {
        console.log('Usuarios recibidos:', usuarios);
        this.usuarios = usuarios.map(usuario => ({
          id: usuario.id || '',
          nombre: usuario.nombre || '',
          correo: usuario.correo || '',
          rol: usuario.rol || '',
          apellido: usuario.apellido || '',
          organizacion: usuario.organizacion || ''
        }));
        console.log('Usuarios mapeados:', this.usuarios);
        this.cargandoUsuarios = false;
      },
      error: (error: any) => {
        console.error('Error al cargar usuarios contratistas:', error);
        this.cargandoUsuarios = false;
      }
    });
  }
}


