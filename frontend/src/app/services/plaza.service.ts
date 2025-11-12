import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import {
  Plaza,
  CrearPlazaDTO,
  AprobarPlazaDTO,
  RechazarPlazaDTO,
  SuspenderPlazaDTO,
  EstadoPlaza,
} from '../models/plaza';
import { environment } from '../../environments/environments';

@Injectable({
  providedIn: 'root',
})
export class PlazaService {
  private apiUrl = `${environment.apiBaseUrl}/admin/plazas`;

  constructor(private http: HttpClient) {}

  private getHeaders(): HttpHeaders {
    const token = localStorage.getItem('clerk_token') || '';
    return new HttpHeaders({
      'Content-Type': 'application/json',
      Authorization: token ? `Bearer ${token}` : '',
    });
  }

  obtenerTodas(): Observable<Plaza[]> {
    return this.http.get<Plaza[]>(this.apiUrl, { headers: this.getHeaders() });
  }

  obtenerPorId(id: number): Observable<Plaza> {
    return this.http.get<Plaza>(`${this.apiUrl}/${id}`, { headers: this.getHeaders() });
  }

  obtenerPendientes(): Observable<Plaza[]> {
    return this.http.get<Plaza[]>(`${this.apiUrl}/pendientes`, { headers: this.getHeaders() });
  }

  obtenerPorEstado(estado: EstadoPlaza | string): Observable<Plaza[]> {
    return this.http.get<Plaza[]>(`${this.apiUrl}/estado/${estado}`, {
      headers: this.getHeaders(),
    });
  }

  buscar(termino: string): Observable<Plaza[]> {
    return this.http.get<Plaza[]>(`${this.apiUrl}/buscar?q=${encodeURIComponent(termino)}`, {
      headers: this.getHeaders(),
    });
  }

  crear(payload: CrearPlazaDTO): Observable<Plaza> {
    return this.http.post<Plaza>(this.apiUrl, payload, { headers: this.getHeaders() });
  }

  aprobar(id: number, datos: AprobarPlazaDTO): Observable<Plaza> {
    return this.http.post<Plaza>(`${this.apiUrl}/${id}/aprobar`, datos, {
      headers: this.getHeaders(),
    });
  }

  rechazar(id: number, datos: RechazarPlazaDTO): Observable<Plaza> {
    return this.http.post<Plaza>(`${this.apiUrl}/${id}/rechazar`, datos, {
      headers: this.getHeaders(),
    });
  }

  suspender(id: number, datos: SuspenderPlazaDTO): Observable<Plaza> {
    return this.http.post<Plaza>(`${this.apiUrl}/${id}/suspender`, datos, {
      headers: this.getHeaders(),
    });
  }

  activar(id: number): Observable<Plaza> {
    return this.http.post<Plaza>(
      `${this.apiUrl}/${id}/activar`,
      {},
      { headers: this.getHeaders() }
    );
  }

  obtenerConPagosVencidos(): Observable<Plaza[]> {
    return this.http.get<Plaza[]>(`${this.apiUrl}/pagos-vencidos`, { headers: this.getHeaders() });
  }
}
