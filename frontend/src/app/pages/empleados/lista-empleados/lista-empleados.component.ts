import { Component, OnInit } from '@angular/core';
import { EmpleadoService } from '../../../services/empleado.service';
import { Empleado } from '../../../models/empleado';
import { CommonModule } from '@angular/common';
import { AuthService } from '../../../services/auth.service';

@Component({
  selector: 'app-lista-empleados',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './lista-empleados.component.html',
  styleUrls: ['./lista-empleados.component.css'],
})
export class ListaEmpleadosComponent implements OnInit {
  empleados: Empleado[] = [];
  cargando = true;
  error = '';

  constructor(
    private empleadoService: EmpleadoService, 
    private authService: AuthService
  ) {}

  async ngOnInit() {
    console.log('[ListaEmpleadosComponent] ngOnInit ejecutado');
    
    // Obtener token JWT
    const token = await this.authService.getToken();
    console.log('[ListaEmpleadosComponent] Token:', token ? 'Token válido' : 'Sin token');

    if (!token) {
      this.error = 'No se encontró el token de autenticación.';
      this.cargando = false;
      console.error('[ListaEmpleadosComponent] Error: Sin token');
      return;
    }

    // Obtener empleados con token
    console.log('[ListaEmpleadosComponent] Ejecutando getEmpleados()');
    this.empleadoService.getEmpleados(token).subscribe({
      next: (data) => {
        console.log('[ListaEmpleadosComponent] Respuesta del backend:', data);
        this.empleados = data;
        this.cargando = false;
      },
      error: (err) => {
        console.error('[ListaEmpleadosComponent] Error al obtener empleados:', err);
        this.error = 'No se pudieron cargar los empleados.';
        this.cargando = false;
      },
    });
  }
}