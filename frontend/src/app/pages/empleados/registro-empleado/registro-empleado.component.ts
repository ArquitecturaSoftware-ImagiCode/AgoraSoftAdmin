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
  enviando: boolean = false; // 🆕 Estado de carga

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
    // Validar formulario
    if (!this.empleadoForm.valid) {
      this.mensaje = 'Por favor complete los campos requeridos ⚠️';
      return;
    }

    this.enviando = true;
    this.mensaje = '';

    try {
      // 🔑 Obtener token de autenticación
      const token = await this.authService.getToken();
      
      if (!token) {
        this.mensaje = 'No se pudo obtener token de autenticación ❌';
        this.enviando = false;
        return;
      }

      // 📨 Enviar datos con el token
      this.empleadoService
        .crearEmpleado(this.empleadoForm.value, token)
        .subscribe({
          next: () => {
            this.mensaje = 'Empleado registrado con éxito ✅';
            this.empleadoForm.reset(); // 🆕 Limpiar formulario tras éxito
            this.enviando = false;
          },
          error: (err) => {
            console.error('[RegistroEmpleadoComponent] Error al registrar:', err);
            this.mensaje = 'Error al registrar el empleado ❌';
            this.enviando = false;
          },
        });
    } catch (err) {
      console.error('[RegistroEmpleadoComponent] Error obteniendo token:', err);
      this.mensaje = 'No se pudo obtener token de autenticación ❌';
      this.enviando = false;
    }
  }
}