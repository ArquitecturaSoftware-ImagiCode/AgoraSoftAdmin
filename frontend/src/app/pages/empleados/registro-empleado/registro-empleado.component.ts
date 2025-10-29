import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { EmpleadoService } from '../../../services/empleado.service';
import { ReactiveFormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { AuthService } from '../../../services/auth.service';

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

  constructor(
    private fb: FormBuilder,
    private empleadoService: EmpleadoService,
    private authService: AuthService
  ) {
    this.empleadoForm = this.fb.group({
      nombre: ['', Validators.required],
      apellido: ['', Validators.required],
      correo: ['', [Validators.required, Validators.email]],
      telefono: ['', Validators.required],
      rol: ['', Validators.required],
    });
  }

  async registrarEmpleado() {
    if (this.empleadoForm.valid) {
      try {
        // 🔑 Obtener token desde el servicio de autenticación
        const token = await this.authService.getToken();

        // 📨 Enviar los datos con el token
        this.empleadoService
          .crearEmpleado(this.empleadoForm.value, token ?? undefined)
          .subscribe({
            next: () => (this.mensaje = 'Empleado registrado con éxito ✅'),
            error: () => (this.mensaje = 'Error al registrar el empleado ❌'),
          });
      } catch (err) {
        this.mensaje = 'No se pudo obtener token de autenticación ❌';
      }
    } else {
      this.mensaje = 'Por favor complete los campos requeridos ⚠️';
    }
  }
}
