import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { HistorialPagoService } from '../../../services/historial-pago.service';
import { OrganizationService } from '../../../services/organization.service';
import { HistorialPago } from '../../../models/HistorialPago';
import { Organization } from '../../../models/Organization';
import { TipoPago, EstadoTransaccion } from '../../../models/Enums';

@Component({
  selector: 'app-registro-pago',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './registro-pago.html',
  styleUrl: './registro-pago.css'
})
export class RegistroPagoComponent implements OnInit {
  nuevoPago: HistorialPago = new HistorialPago();
  organizaciones: Organization[] = [];
  tiposPago = Object.values(TipoPago);
  metodosPago = ['TRANSFERENCIA', 'EFECTIVO', 'TARJETA', 'CHEQUE'];
  
  cargandoOrganizaciones: boolean = true;
  enviando: boolean = false;
  mensajeExito: string = '';
  mensajeError: string = '';

  constructor(
    private historialPagoService: HistorialPagoService,
    private organizationService: OrganizationService
  ) {}

  ngOnInit() {
    this.cargarOrganizaciones();
    this.inicializarFormulario();
  }

  inicializarFormulario() {
    this.nuevoPago = new HistorialPago();
    this.nuevoPago.fechaPago = new Date();
    this.nuevoPago.estado = EstadoTransaccion.COMPLETADO;
    this.nuevoPago.tipoPago = TipoPago.SERVICIO;
    this.nuevoPago.metodoPago = 'TRANSFERENCIA';
  }

  cargarOrganizaciones() {
    this.cargandoOrganizaciones = true;
    this.organizationService.getOrganizations().subscribe({
      next: (organizaciones: Organization[]) => {
        this.organizaciones = organizaciones;
        this.cargandoOrganizaciones = false;
      },
      error: (error: any) => {
        console.error('Error al cargar organizaciones:', error);
        this.mensajeError = 'Error al cargar organizaciones';
        this.cargandoOrganizaciones = false;
      }
    });
  }

  onSubmit() {
    if (!this.validarFormulario()) {
      return;
    }

    this.enviando = true;
    this.mensajeExito = '';
    this.mensajeError = '';

    console.log('Enviando pago:', this.nuevoPago);

    // Crear objeto limpio solo con los campos necesarios
    const pagoParaEnviar: HistorialPago = {
      tipoPago: this.nuevoPago.tipoPago,
      monto: this.nuevoPago.monto,
      metodoPago: this.nuevoPago.metodoPago,
      descripcion: this.nuevoPago.descripcion,
      comprobante: this.nuevoPago.comprobante,
      estado: this.nuevoPago.estado,
      fechaPago: this.nuevoPago.fechaPago,
      registradoPorEmpleadoId: 1 // ID del empleado con sesión iniciada (hardcodeado por ahora)
    };

    // Agregar campos específicos según el tipo de pago
    if (this.nuevoPago.tipoPago === 'SERVICIO' && this.nuevoPago.organizacionId) {
      pagoParaEnviar.organizacionId = this.nuevoPago.organizacionId;
    }

    if (this.nuevoPago.tipoPago === 'NOMINA' && this.nuevoPago.empleadoId) {
      pagoParaEnviar.empleadoId = this.nuevoPago.empleadoId;
    }

    console.log('Pago limpio para enviar:', pagoParaEnviar);

    this.historialPagoService.crearPago(pagoParaEnviar).subscribe({
      next: (pagoCreado: HistorialPago) => {
        console.log('Pago creado exitosamente:', pagoCreado);
        this.mensajeExito = 'Pago registrado exitosamente';
        this.inicializarFormulario();
        this.enviando = false;
      },
      error: (error: any) => {
        console.error('Error al crear pago:', error);
        this.mensajeError = 'Error al registrar el pago. Inténtalo de nuevo.';
        this.enviando = false;
      }
    });
  }

  validarFormulario(): boolean {
    if (!this.nuevoPago.tipoPago) {
      this.mensajeError = 'Debe seleccionar un tipo de pago';
      return false;
    }
    if (!this.nuevoPago.monto || this.nuevoPago.monto <= 0) {
      this.mensajeError = 'Debe ingresar un monto válido';
      return false;
    }
    if (!this.nuevoPago.metodoPago) {
      this.mensajeError = 'Debe seleccionar un método de pago';
      return false;
    }
    if (!this.nuevoPago.descripcion || this.nuevoPago.descripcion.trim() === '') {
      this.mensajeError = 'Debe ingresar una descripción';
      return false;
    }
    return true;
  }

  limpiarMensajes() {
    this.mensajeExito = '';
    this.mensajeError = '';
  }

  obtenerDescripcionTipoPago(tipo: string): string {
    switch (tipo) {
      case TipoPago.NOMINA: return 'Nómina';
      case TipoPago.SERVICIO: return 'Servicio';
      case TipoPago.OTRO: return 'Otro';
      default: return tipo;
    }
  }
}
