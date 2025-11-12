import { CommonModule } from '@angular/common';
import { Component, OnInit, ViewChild, ElementRef } from '@angular/core';
import { FormBuilder, FormGroup, Validators, ReactiveFormsModule } from '@angular/forms';
import axios from 'axios';
import { LucideAngularModule, PlusIcon } from 'lucide-angular';
import { environment } from '../../../../environments/environments';
import { AuthService } from '../../../services/auth.service';

@Component({
  selector: 'app-modulos-arquitectura',
  standalone: true,
  imports: [LucideAngularModule, ReactiveFormsModule, CommonModule],
  templateUrl: './modulos-arquitectura.html',
  styleUrl: './modulos-arquitectura.css',
})
export class ModulosArquitectura implements OnInit {
  readonly PlusIcon = PlusIcon;

  @ViewChild('modalCrear') modalCrear!: ElementRef<HTMLDialogElement>;
  @ViewChild('modalEditar') modalEditar!: ElementRef<HTMLDialogElement>;

  moduloForm: FormGroup;
  editarForm: FormGroup;
  modulos: any[] = [];
  moduloEditando: any = null;

  ngOnInit(): void {
    this.getModulos();
  }

  constructor(private fb: FormBuilder, private authService: AuthService) {
    this.moduloForm = this.fb.group({
      nombre: ['', [Validators.required, Validators.minLength(3)]],
      descripcion: ['', [Validators.required, Validators.minLength(10)]],
      icono: ['', Validators.required],
      precioMensual: ['', [Validators.required, Validators.min(0)]],
      activo: ['', Validators.required]
    });

    this.editarForm = this.fb.group({
      nombre: ['', [Validators.required, Validators.minLength(3)]],
      descripcion: ['', [Validators.required, Validators.minLength(10)]],
      icono: ['', Validators.required],
      precioMensual: ['', [Validators.required, Validators.min(0)]]
    });
  }

  // Métodos para manejar modales
  abrirModalCrear(): void {
    if (this.modalCrear?.nativeElement) {
      this.modalCrear.nativeElement.showModal();
    }
  }

  cerrarModalCrear(): void {
    if (this.modalCrear?.nativeElement) {
      this.modalCrear.nativeElement.close();
    }
  }

  abrirModalEditar(modulo: any): void {
    this.moduloEditando = modulo;
    this.editarForm.patchValue({
      nombre: modulo.nombre,
      descripcion: modulo.descripcion,
      icono: modulo.icono,
      precioMensual: modulo.precioMensual
    });

    if (this.modalEditar?.nativeElement) {
      this.modalEditar.nativeElement.showModal();
    }
  }

  cerrarModalEditar(): void {
    if (this.modalEditar?.nativeElement) {
      this.modalEditar.nativeElement.close();
    }
    this.moduloEditando = null;
    this.editarForm.reset();
  }

  async getModulos() {
    try {
      const token = (await this.authService.getToken()) || '';
      const response = await axios.get(`${environment.apiBaseUrl}/admin/modulos`, {
        headers: {
          Authorization: `Bearer ${token}`,
          'Content-Type': 'application/json',
        },
      });

      console.log('Módulos obtenidos:', response.data);
      this.modulos = response.data;
    } catch (error) {
      console.error('Error al obtener módulos:', error);
      this.mostrarMensajeError('Error al cargar los módulos');
    }
  }

  async onSubmit() {
    if (this.moduloForm.invalid) {
      this.moduloForm.markAllAsTouched();
      return;
    }

    try {
      const token = (await this.authService.getToken()) || '';
      const formData = {
        ...this.moduloForm.value,
        precioMensual: parseFloat(this.moduloForm.value.precioMensual)
      };

      console.log('Creando módulo:', formData);

      const response = await axios.post(
        `${environment.apiBaseUrl}/admin/modulos`,
        formData,
        {
          headers: {
            Authorization: `Bearer ${token}`,
            'Content-Type': 'application/json',
          },
        }
      );

      console.log('Módulo creado:', response.data);
      
      // Resetear formulario y cerrar modal
      this.moduloForm.reset();
      this.cerrarModalCrear();
      
      // Recargar la lista de módulos
      this.getModulos();
      
      this.mostrarMensajeExito('Módulo creado correctamente');
      
    } catch (error: any) {
      console.error('Error al crear módulo:', error);
      const errorMessage = error.response?.data?.error || 'Error al crear el módulo';
      this.mostrarMensajeError(errorMessage);
    }
  }

  async onEditarSubmit() {
    if (this.editarForm.invalid || !this.moduloEditando) {
      this.editarForm.markAllAsTouched();
      return;
    }

    try {
      const token = (await this.authService.getToken()) || '';
      const formData = {
        ...this.editarForm.value,
        precioMensual: parseFloat(this.editarForm.value.precioMensual),
        activo: this.moduloEditando.activo // Mantener el estado actual
      };

      console.log('Actualizando módulo:', this.moduloEditando.id, formData);

      const response = await axios.put(
        `${environment.apiBaseUrl}/admin/modulos/${this.moduloEditando.id}`,
        formData,
        {
          headers: {
            Authorization: `Bearer ${token}`,
            'Content-Type': 'application/json',
          },
        }
      );

      console.log('Módulo actualizado:', response.data);

      this.cerrarModalEditar();
      this.getModulos(); // Recargar para ver cambios
      this.mostrarMensajeExito('Módulo actualizado correctamente');
      
    } catch (error: any) {
      console.error('Error al editar módulo:', error);
      const errorMessage = error.response?.data?.error || 'Error al actualizar el módulo';
      this.mostrarMensajeError(errorMessage);
    }
  }

  async toggleEstadoModulo(moduloId: number) {
    try {
      const token = (await this.authService.getToken()) || '';
      
      console.log('Cambiando estado del módulo:', moduloId);

      const response = await axios.post(
        `${environment.apiBaseUrl}/admin/modulos/${moduloId}/toggle-activo`,
        {},
        {
          headers: {
            Authorization: `Bearer ${token}`,
            'Content-Type': 'application/json',
          },
        }
      );

      console.log('Estado cambiado:', response.data);

      this.getModulos(); // Recargar lista
      this.mostrarMensajeExito('Estado del módulo actualizado correctamente');
      
    } catch (error: any) {
      console.error('Error al cambiar estado del módulo:', error);
      const errorMessage = error.response?.data?.error || 'Error al cambiar el estado del módulo';
      this.mostrarMensajeError(errorMessage);
    }
  }

  // Métodos de ayuda para validación
  campoInvalido(campo: string): boolean {
    const control = this.moduloForm.get(campo);
    return !!(control && control.invalid && control.touched);
  }

  campoInvalidoEditar(campo: string): boolean {
    const control = this.editarForm.get(campo);
    return !!(control && control.invalid && control.touched);
  }

  // Métodos auxiliares para mensajes
  private mostrarMensajeExito(mensaje: string): void {
    // Puedes implementar un toast o alert aquí
    alert(`✅ ${mensaje}`);
  }

  private mostrarMensajeError(mensaje: string): void {
    // Puedes implementar un toast o alert aquí
    alert(`❌ ${mensaje}`);
  }
}