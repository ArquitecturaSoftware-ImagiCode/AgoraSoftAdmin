import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { HistorialPago } from '../models/HistorialPago';

@Injectable({
  providedIn: 'root'
})
export class HistorialPagoService {
  private apiUrl = 'http://localhost:8085/api/historial-pagos';

  constructor(private http: HttpClient) {}

  // GET: traer todos los pagos
  getPagos(): Observable<HistorialPago[]> {
    return this.http.get<HistorialPago[]>(this.apiUrl);
  }

  // GET: traer pago por id
  getPagoPorId(id: number): Observable<HistorialPago> {
    return this.http.get<HistorialPago>(`${this.apiUrl}/${id}`);
  }

  // POST: crear pago
  crearPago(pago: HistorialPago): Observable<HistorialPago> {
    return this.http.post<HistorialPago>(this.apiUrl, pago);
  }

  // PUT: actualizar pago
  actualizarPago(id: number, pago: HistorialPago): Observable<HistorialPago> {
    return this.http.put<HistorialPago>(`${this.apiUrl}/${id}`, pago);
  }

  // DELETE: eliminar pago
  eliminarPago(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`);
  }

  // GET: traer pagos por tipo
  getPagosPorTipo(tipoPago: string): Observable<HistorialPago[]> {
    return this.http.get<HistorialPago[]>(`${this.apiUrl}/tipo/${tipoPago}`);
  }

  // GET: traer pagos de nómina
  getPagosNomina(): Observable<HistorialPago[]> {
    return this.http.get<HistorialPago[]>(`${this.apiUrl}/nomina`);
  }

  // GET: traer pagos de servicios
  getPagosServicios(): Observable<HistorialPago[]> {
    return this.http.get<HistorialPago[]>(`${this.apiUrl}/servicios`);
  }

  // GET: traer pagos del mes actual
  getPagosMesActual(): Observable<HistorialPago[]> {
    return this.http.get<HistorialPago[]>(`${this.apiUrl}/mes-actual`);
  }

  // PUT: marcar como completado
  marcarComoCompletado(id: number): Observable<HistorialPago> {
    return this.http.put<HistorialPago>(`${this.apiUrl}/${id}/completar`, {});
  }

  // PUT: marcar como pendiente
  marcarComoPendiente(id: number): Observable<HistorialPago> {
    return this.http.put<HistorialPago>(`${this.apiUrl}/${id}/pendiente`, {});
  }

  // PUT: marcar como cancelado
  marcarComoCancelado(id: number): Observable<HistorialPago> {
    return this.http.put<HistorialPago>(`${this.apiUrl}/${id}/cancelar`, {});
  }
}
