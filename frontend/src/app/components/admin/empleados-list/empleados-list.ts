import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Router, RouterModule } from '@angular/router';
import { ReactiveFormsModule, FormControl } from '@angular/forms';
import { catchError, of, debounceTime, switchMap, filter } from 'rxjs';
import { EmpleadoService } from '../../../services/empleado.service';
import { Empleado } from '../../../models/empleado.model';

@Component({
  selector: 'app-empleados-list',
  standalone: true,
  imports: [CommonModule, RouterModule, ReactiveFormsModule],
  templateUrl: './empleados-list.html',
  styleUrls: ['./empleados-list.css']
})
export class EmpleadosListComponent implements OnInit {
  empleados: Empleado[] = [];
  loading = false;
  error = '';
  searchControl = new FormControl('');

  constructor(private empleadoService: EmpleadoService, private router: Router) {}

  ngOnInit(): void {
    this.cargarEmpleados();

    // búsqueda con debounce
    this.searchControl.valueChanges.pipe(
      // Filtrar valores nulos
      // Solo pasar strings
      // Puedes usar filter de rxjs
      // Importa 'filter' si no está importado
      // import { filter } from 'rxjs';
      filter((term): term is string => term !== null),
      debounceTime(300),
      switchMap((term: string) => {
        if (!term) return this.empleadoService.obtenerTodos();
        return this.empleadoService.buscar(term);
      }),
      catchError(err => {
        console.warn('Error búsqueda empleados', err);
        this.error = 'Error al buscar empleados';
        return of([]);
      })
    ).subscribe((list: Empleado[] | any) => {
      // cuando backend devuelve objeto, manejar
      this.empleados = Array.isArray(list) ? list : [];
    });
  }

  cargarEmpleados(): void {
    this.loading = true;
    this.empleadoService.obtenerTodos().subscribe({
      next: res => { this.empleados = res; this.loading = false; },
      error: err => { console.error(err); this.error = 'No se pudieron cargar empleados.'; this.loading = false; }
    });
  }

  crearEmpleado(): void {
    this.router.navigate(['/admin/empleados/nuevo']);
  }

  verEmpleado(id: number): void {
    this.router.navigate(['/admin/empleados', id]);
  }

  editarEmpleado(id: number): void {
    this.router.navigate(['/admin/empleados', id, 'editar']);
  }

  toggleActivo(e: Empleado): void {
    if (e.activo) {
      this.empleadoService.desactivar(e.id).subscribe({
        next: () => { e.activo = false; },
        error: err => { console.error(err); alert('Error desactivando empleado'); }
      });
    } else {
      this.empleadoService.activar(e.id).subscribe({
        next: () => { e.activo = true; },
        error: err => { console.error(err); alert('Error activando empleado'); }
      });
    }
  }
}
