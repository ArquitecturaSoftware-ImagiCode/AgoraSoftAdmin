import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ActivatedRoute, Router, RouterModule } from '@angular/router';
import { EmpleadoService } from '../../../services/empleado.service';
import { Empleado } from '../../../models/empleado.model';

@Component({
  selector: 'app-empleado-detail',
  standalone: true,
  imports: [CommonModule, RouterModule],
  templateUrl: './empleado-detail.html',
  styleUrls: ['./empleado-detail.css']
})
export class EmpleadoDetailComponent implements OnInit {
  empleado: Empleado | null = null;
  loading = false;
  error = '';

  constructor(private route: ActivatedRoute, private empleadoService: EmpleadoService, private router: Router) {}

  ngOnInit(): void {
    const id = this.route.snapshot.paramMap.get('id');
    if (id) this.cargarEmpleado(+id);
  }

  cargarEmpleado(id: number): void {
    this.loading = true;
    this.empleadoService.obtenerPorId(id).subscribe({
      next: e => { this.empleado = e; this.loading = false; },
      error: err => { console.error(err); this.error = 'No se pudo cargar empleado'; this.loading = false; }
    });
  }

  volver(): void { this.router.navigate(['/admin/empleados']); }
  editar(): void { if (this.empleado) this.router.navigate(['/admin/empleados', this.empleado.id, 'editar']); }
}
