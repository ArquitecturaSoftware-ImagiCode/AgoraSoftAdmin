import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { EmpleadoService } from '../../../services/empleado.service';
import { ReactiveFormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';

@Component({
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule],
  selector: 'app-registro-empleado',
  templateUrl: './registro-empleado.component.html',
  styleUrls: ['./registro-empleado.component.css'],
})
export class RegistroEmpleadoComponent {
  empleadoForm: FormGroup;
  mensaje: string = '';

  constructor(private fb: FormBuilder, private empleadoService: EmpleadoService) {
    this.empleadoForm = this.fb.group({
      nombre: ['', Validators.required],
      apellido: ['', Validators.required],
      correo: ['', [Validators.required, Validators.email]],
      telefono: ['', Validators.required],
      rol: ['', Validators.required],
    });
  }

  registrarEmpleado() {
    if (this.empleadoForm.valid) {
      this.empleadoService.crearEmpleado(this.empleadoForm.value).subscribe({
        next: () => (this.mensaje = 'Empleado registrado con éxito ✅'),
        error: () => (this.mensaje = 'Error al registrar el empleado ❌'),
      });
    }
  }
}
