import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Plaza, CrearPlazaDTO, AprobarPlazaDTO, RechazarPlazaDTO, SuspenderPlazaDTO, EstadoPlaza } from '../models/plaza.model';
import { environment } from '../../environments/environments';

@Injectable({
  providedIn: 'root'
})
export class PlazaService {
  private apiUrl = `${environment.apiBaseUrl}/admin/plazas`;

  constructor(private http: HttpClient) {}

  private getHeaders(): HttpHeaders {
    const token = localStorage.getItem('clerk_token');
    return new HttpHeaders({
      'Content-Type': 'application/json',
      'Authorization': `Bearer ${token}`
    });
  }

  // Obtener todas las plazas
  obtenerTodas(): Observable<Plaza[]> {
    return this.http.get<Plaza[]>(this.apiUrl, { headers: this.getHeaders() });
  }

  // Obtener plaza por ID
  obtenerPorId(id: number): Observable<Plaza> {
    return this.http.get<Plaza>(`${this.apiUrl}/${id}`, { headers: this.getHeaders() });
  }

  // Obtener plazas pendientes
  obtenerPendientes(): Observable<Plaza[]> {
    return this.http.get<Plaza[]>(`${this.apiUrl}/pendientes`, { headers: this.getHeaders() });
  }

  // Obtener plazas por estado
  obtenerPorEstado(estado: EstadoPlaza): Observable<Plaza[]> {
    return this.http.get<Plaza[]>(`${this.apiUrl}/estado/${estado}`, { headers: this.getHeaders() });
  }

  // Buscar plazas
  buscar(termino: string): Observable<Plaza[]> {
    return this.http.get<Plaza[]>(`${this.apiUrl}/buscar?q=${termino}`, { headers: this.getHeaders() });
  }

  // Aprobar plaza
  aprobar(id: number, datos: AprobarPlazaDTO): Observable<Plaza> {
    return this.http.post<Plaza>(`${this.apiUrl}/${id}/aprobar`, datos, { headers: this.getHeaders() });
  }

  // Rechazar plaza
  rechazar(id: number, datos: RechazarPlazaDTO): Observable<Plaza> {
    return this.http.post<Plaza>(`${this.apiUrl}/${id}/rechazar`, datos, { headers: this.getHeaders() });
  }

  // Suspender plaza
  suspender(id: number, datos: SuspenderPlazaDTO): Observable<Plaza> {
    return this.http.post<Plaza>(`${this.apiUrl}/${id}/suspender`, datos, { headers: this.getHeaders() });
  }

  // Activar plaza
  activar(id: number): Observable<Plaza> {
    return this.http.post<Plaza>(`${this.apiUrl}/${id}/activar`, {}, { headers: this.getHeaders() });
  }

  // Obtener plazas con pagos vencidos
  obtenerConPagosVencidos(): Observable<Plaza[]> {
    return this.http.get<Plaza[]>(`${this.apiUrl}/pagos-vencidos`, { headers: this.getHeaders() });
  }
}
