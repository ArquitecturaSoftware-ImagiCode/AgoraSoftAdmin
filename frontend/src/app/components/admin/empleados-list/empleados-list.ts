import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule, Router } from '@angular/router';
import { ReactiveFormsModule, FormControl } from '@angular/forms';
import { catchError, debounceTime, switchMap, of, filter } from 'rxjs';
import { EmpleadoService } from '../../../services/empleado.service';
import { Empleado } from '../../../models/empleado.model';

@Component({
  selector: 'app-lista-empleados',
  standalone: true,
  imports: [CommonModule, RouterModule, ReactiveFormsModule],
  templateUrl: './empleados-list.html',
  styleUrls: ['./empleados-list.css'],
})
export class ListaEmpleadosComponent implements OnInit {
  empleados: Empleado[] = [];
  loading = false;
  error = '';
  searchControl = new FormControl('');

  constructor(private empleadoService: EmpleadoService, private router: Router) {}

  ngOnInit(): void {
    this.cargarEmpleados();

    this.searchControl.valueChanges
      .pipe(
        debounceTime(300),
        filter((term): term is string => term !== null), // ✅ filtramos `null` aquí
        switchMap((term: string) => {
          if (!term.trim()) return this.empleadoService.obtenerTodos();
          return this.empleadoService.buscar(term);
        }),
        catchError((err) => {
          console.warn('Error búsqueda empleados', err);
          this.error = 'Error al buscar empleados';
          return of([]);
        })
      )
      .subscribe((list: Empleado[]) => {
        this.empleados = Array.isArray(list) ? list : [];
      });
  }

  cargarEmpleados(): void {
    this.loading = true;
    this.empleadoService.obtenerTodos().subscribe({
      next: (data: Empleado[]) => {
        this.empleados = data;
        this.loading = false;
      },
      error: (err) => {
        console.error(err);
        this.error = 'No se pudieron cargar empleados.';
        this.loading = false;
      },
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
        next: () => {
          e.activo = false;
        },
        error: (err) => {
          console.error(err);
          alert('Error desactivando empleado');
        },
      });
    } else {
      this.empleadoService.activar(e.id).subscribe({
        next: () => {
          e.activo = true;
        },
        error: (err) => {
          console.error(err);
          alert('Error activando empleado');
        },
      });
    }
  }
}
