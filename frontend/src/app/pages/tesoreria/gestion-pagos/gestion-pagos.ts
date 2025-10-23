import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';

type Plaza = {
  id: number;
  nombre: string;
  estadoPago: 'pendiente' | 'pagado';
  monto: number;
  fechaLimite: string;
};

type UsuarioEmpresa = {
  id: number;
  nombre: string;
  correo: string;
  rol: string;
};

@Component({
  selector: 'app-gestion-pagos',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './gestion-pagos.html',
  styleUrl: './gestion-pagos.css',
})
export class GestionPagosPage {
  plazas: Plaza[] = [
    { id: 1, nombre: 'Plaza Central', estadoPago: 'pendiente', monto: 1500000, fechaLimite: '2025-10-31' },
    { id: 2, nombre: 'Plaza Norte', estadoPago: 'pagado', monto: 980000, fechaLimite: '2025-09-30' },
    { id: 3, nombre: 'Plaza Sur', estadoPago: 'pendiente', monto: 720000, fechaLimite: '2025-11-15' },
    { id: 4, nombre: 'Plaza Occidente', estadoPago: 'pagado', monto: 1260000, fechaLimite: '2025-09-20' },
  ];

  usuarios: UsuarioEmpresa[] = [
    { id: 1, nombre: 'Ana Gómez', correo: 'ana@miempresa.com', rol: 'tesoreria' },
    { id: 2, nombre: 'Carlos Ruiz', correo: 'carlos@miempresa.com', rol: 'auditoria' },
    { id: 3, nombre: 'María López', correo: 'maria@miempresa.com', rol: 'soporte' },
    { id: 4, nombre: 'Juan Pérez', correo: 'juan@miempresa.com', rol: 'arquitectura' },
  ];

  get plazasPendientes(): Plaza[] {
    return this.plazas.filter((p) => p.estadoPago === 'pendiente');
  }

  get plazasPagadas(): Plaza[] {
    return this.plazas.filter((p) => p.estadoPago === 'pagado');
  }
}


