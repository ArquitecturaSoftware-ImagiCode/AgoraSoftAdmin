import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Empleado, CrearEmpleadoDTO, ActualizarEmpleadoDTO, RolEmpleado } from '../models/empleado';
import { environment } from '../../environments/environments';

@Injectable({
  providedIn: 'root'
})
export class EmpleadoService {
  private apiUrl = `${environment.apiBaseUrl}/admin/empleados`;

  constructor(private http: HttpClient) {}

  /**
   * Construye los headers con token si está disponible
   */
  private getHeaders(token?: string): HttpHeaders {
    let headers = new HttpHeaders({ 'Content-Type': 'application/json' });
    if (token) {
      headers = headers.set('Authorization', `Bearer ${token}`);
    }
    return headers;
  }

  // ========== Métodos principales (usados en componentes actuales) ==========

  /**
   * Obtener todos los empleados
   */
  getEmpleados(token?: string): Observable<Empleado[]> {
    return this.http.get<Empleado[]>(this.apiUrl, { 
      headers: this.getHeaders(token) 
    });
  }

  /**
   * Crear nuevo empleado
   */
  crearEmpleado(empleado: Empleado | CrearEmpleadoDTO, token?: string): Observable<Empleado> {
    return this.http.post<Empleado>(this.apiUrl, empleado, { 
      headers: this.getHeaders(token) 
    });
  }

  // ========== Métodos extendidos (de Servicio-de-correo-automatico) ==========

  /**
   * Obtener todos los empleados (alias para compatibilidad)
   */
  obtenerTodos(token?: string): Observable<Empleado[]> {
    return this.getEmpleados(token);
  }

  /**
   * Obtener empleado por ID
   */
  obtenerPorId(id: number, token?: string): Observable<Empleado> {
    return this.http.get<Empleado>(`${this.apiUrl}/${id}`, { 
      headers: this.getHeaders(token) 
    });
  }

  /**
   * Obtener empleado actual (usuario logueado)
   */
  obtenerEmpleadoActual(token?: string): Observable<Empleado> {
    return this.http.get<Empleado>(`${this.apiUrl}/me`, { 
      headers: this.getHeaders(token) 
    });
  }

  /**
   * Crear empleado (alias con DTO tipado)
   */
  crear(empleado: CrearEmpleadoDTO, token?: string): Observable<Empleado> {
    return this.crearEmpleado(empleado, token);
  }

  /**
   * Actualizar empleado
   */
  actualizar(id: number, empleado: ActualizarEmpleadoDTO, token?: string): Observable<Empleado> {
    return this.http.put<Empleado>(`${this.apiUrl}/${id}`, empleado, {
      headers: this.getHeaders(token)
    });
  }

  /**
   * Desactivar empleado
   */
  desactivar(id: number, token?: string): Observable<Empleado> {
    return this.http.post<Empleado>(
      `${this.apiUrl}/${id}/desactivar`,
      {},
      { headers: this.getHeaders(token) }
    );
  }

  /**
   * Activar empleado
   */
  activar(id: number, token?: string): Observable<Empleado> {
    return this.http.post<Empleado>(
      `${this.apiUrl}/${id}/activar`,
      {},
      { headers: this.getHeaders(token) }
    );
  }

  /**
   * Buscar empleados por término
   */
  buscar(termino: string, token?: string): Observable<Empleado[]> {
    return this.http.get<Empleado[]>(`${this.apiUrl}/buscar?q=${encodeURIComponent(termino)}`, {
      headers: this.getHeaders(token)
    });
  }

  /**
   * Obtener empleados activos
   */
  obtenerActivos(token?: string): Observable<Empleado[]> {
    return this.http.get<Empleado[]>(`${this.apiUrl}/activos`, { 
      headers: this.getHeaders(token) 
    });
  }

  /**
   * Obtener empleados por rol
   */
  obtenerPorRol(rol: RolEmpleado, token?: string): Observable<Empleado[]> {
    return this.http.get<Empleado[]>(`${this.apiUrl}/rol/${rol}`, { 
      headers: this.getHeaders(token) 
    });
  }

  /**
   * Obtener empleados por departamento
   */
  obtenerPorDepartamento(departamento: string, token?: string): Observable<Empleado[]> {
    return this.http.get<Empleado[]>(`${this.apiUrl}/departamento/${encodeURIComponent(departamento)}`, {
      headers: this.getHeaders(token)
    });
  }
}