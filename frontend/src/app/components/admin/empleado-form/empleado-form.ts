import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ReactiveFormsModule, FormGroup, FormBuilder, Validators } from '@angular/forms';
import { Router, ActivatedRoute, RouterModule } from '@angular/router';
import { EmpleadoService } from '../../../services/empleado.service';
import { Empleado, RolEmpleado } from '../../../models/empleado.model';

@Component({
  selector: 'app-empleado-form',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule, RouterModule],
  templateUrl: './empleado-form.html',
  styleUrls: ['./empleado-form.css']
})
export class EmpleadoFormComponent implements OnInit {
  form!: FormGroup;
  loading = false;
  error = '';
  isEdit = false;
  empleadoId?: number;
  roles = Object.values(RolEmpleado);

  constructor(
    private fb: FormBuilder,
    private empleadoService: EmpleadoService,
    private router: Router,
    private route: ActivatedRoute
  ) {}

  ngOnInit(): void {
    this.form = this.fb.group({
      nombre: ['', [Validators.required, Validators.maxLength(100)]],
      apellido: ['', [Validators.required, Validators.maxLength(100)]],
      correo: ['', [Validators.required, Validators.email]],
      rol: [RolEmpleado.ADMIN, Validators.required],
      departamento: ['', Validators.required],
      telefono: ['']
    });

    const id = this.route.snapshot.paramMap.get('id');
    if (id) {
      this.isEdit = true;
      this.empleadoId = +id;
      this.cargarEmpleado(+id);
    }
  }

  cargarEmpleado(id: number): void {
    this.loading = true;
    this.empleadoService.obtenerPorId(id).subscribe({
      next: (e: Empleado) => {
        this.form.patchValue({
          nombre: e.nombre,
          apellido: e.apellido,
          correo: e.correo,
          rol: e.rol,
          departamento: e.departamento,
          telefono: e.telefono
        });
        this.loading = false;
      },
      error: err => { console.error(err); this.error = 'No se pudo cargar empleado'; this.loading = false; }
    });
  }

  submit(): void {
    if (this.form.invalid) return;
    this.loading = true;
    const payload = this.form.value;

    if (this.isEdit && this.empleadoId) {
      this.empleadoService.actualizar(this.empleadoId, payload).subscribe({
        next: () => { this.loading = false; this.router.navigate(['/admin/empleados']); },
        error: err => { console.error(err); this.error = 'Error actualizando empleado'; this.loading = false; }
      });
    } else {
      this.empleadoService.crear(payload).subscribe({
        next: () => { this.loading = false; this.router.navigate(['/admin/empleados']); },
        error: err => { console.error(err); this.error = 'Error creando empleado'; this.loading = false; }
      });
    }
  }

  cancelar(): void {
    this.router.navigate(['/admin/empleados']);
  }
}
