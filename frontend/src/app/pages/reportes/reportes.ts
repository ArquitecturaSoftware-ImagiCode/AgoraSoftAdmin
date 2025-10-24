import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { HistorialPagoService } from '../../services/historial-pago.service';
import { SubscriptionService } from '../../services/subscription.service';
import { HistorialPago } from '../../models/HistorialPago';
import { Subscription } from '../../models/Subscription';

@Component({
  selector: 'app-reportes',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './reportes.html',
  styleUrl: './reportes.css'
})
export class ReportesComponent implements OnInit {
  historialPagos: HistorialPago[] = [];
  suscripciones: Subscription[] = [];
  
  // Estados de carga
  cargandoPagos: boolean = true;
  cargandoSuscripciones: boolean = true;
  
  // Estadísticas
  totalPagos: number = 0;
  totalSuscripciones: number = 0;
  totalIngresos: number = 0;

  constructor(
    private historialPagoService: HistorialPagoService,
    private subscriptionService: SubscriptionService
  ) {}

  ngOnInit() {
    this.cargarDatos();
  }

  cargarDatos() {
    this.cargarHistorialPagos();
    this.cargarSuscripciones();
  }

  cargarHistorialPagos() {
    this.cargandoPagos = true;
    this.historialPagoService.getPagos().subscribe({
      next: (pagos: HistorialPago[]) => {
        this.historialPagos = pagos;
        this.calcularEstadisticasPagos();
        this.cargandoPagos = false;
      },
      error: (error: any) => {
        console.error('Error al cargar historial de pagos:', error);
        this.cargandoPagos = false;
      }
    });
  }

  cargarSuscripciones() {
    this.cargandoSuscripciones = true;
    this.subscriptionService.getSubscriptions().subscribe({
      next: (suscripciones: Subscription[]) => {
        this.suscripciones = suscripciones;
        this.calcularEstadisticasSuscripciones();
        this.cargandoSuscripciones = false;
      },
      error: (error: any) => {
        console.error('Error al cargar suscripciones:', error);
        this.cargandoSuscripciones = false;
      }
    });
  }

  calcularEstadisticasPagos() {
    this.totalPagos = this.historialPagos.length;
    this.totalIngresos = this.historialPagos
      .filter(pago => pago.estado === 'COMPLETADO')
      .reduce((total, pago) => total + (pago.monto || 0), 0);
  }

  calcularEstadisticasSuscripciones() {
    this.totalSuscripciones = this.suscripciones.length;
  }


  exportarPagos() {
    // Implementar exportación de pagos
    console.log('Exportando pagos...');
  }

  exportarSuscripciones() {
    // Implementar exportación de suscripciones
    console.log('Exportando suscripciones...');
  }

  obtenerDescripcionTipoPago(tipo: string): string {
    switch (tipo) {
      case 'NOMINA': return 'Nómina';
      case 'SERVICIO': return 'Servicio';
      case 'SUSCRIPCION': return 'Suscripción';
      case 'OTRO': return 'Otro';
      default: return tipo;
    }
  }

  obtenerDescripcionEstadoPago(estado: string): string {
    switch (estado) {
      case 'COMPLETADO': return 'Completado';
      case 'PENDIENTE': return 'Pendiente';
      case 'FALLIDO': return 'Fallido';
      case 'REEMBOLSADO': return 'Reembolsado';
      default: return estado;
    }
  }

  obtenerDescripcionEstadoSuscripcion(estado: string): string {
    switch (estado) {
      case 'active': return 'Activa';
      case 'incomplete': return 'Incompleta';
      case 'incomplete_expired': return 'Expirada';
      case 'trialing': return 'Prueba';
      case 'past_due': return 'Vencida';
      case 'canceled': return 'Cancelada';
      case 'unpaid': return 'Sin Pago';
      case 'paused': return 'Pausada';
      default: return estado || 'Desconocido';
    }
  }

  obtenerDescripcionTipoSuscripcion(tipo: string): string {
    switch (tipo) {
      case 'BASICA': return 'Básica';
      case 'PREMIUM': return 'Premium';
      case 'ENTERPRISE': return 'Enterprise';
      default: return tipo;
    }
  }

  formatearFecha(fecha: Date | string | undefined): string {
    if (!fecha) return '';
    const fechaObj = typeof fecha === 'string' ? new Date(fecha) : fecha;
    return fechaObj.toLocaleDateString('es-ES');
  }

  formatearMoneda(monto: number): string {
    return new Intl.NumberFormat('es-ES', {
      style: 'currency',
      currency: 'COP'
    }).format(monto);
  }
}
