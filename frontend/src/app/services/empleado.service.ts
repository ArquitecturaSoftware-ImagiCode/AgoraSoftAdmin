import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Empleado, CrearEmpleadoDTO, ActualizarEmpleadoDTO, RolEmpleado } from '../models/empleado.model';
import { environment } from '../../environments/environments';

@Injectable({
  providedIn: 'root'
})
export class EmpleadoService {
  private apiUrl = `${environment.apiBaseUrl}/admin/empleados`;

  constructor(private http: HttpClient) {}

  private getHeaders(): HttpHeaders {
    const token = localStorage.getItem('clerk_token');
    return new HttpHeaders({
      'Content-Type': 'application/json',
      'Authorization': `Bearer ${token}`
    });
  }

  // Obtener todos los empleados
  obtenerTodos(): Observable<Empleado[]> {
    return this.http.get<Empleado[]>(this.apiUrl, { headers: this.getHeaders() });
  }

  // Obtener empleado por ID
  obtenerPorId(id: number): Observable<Empleado> {
    return this.http.get<Empleado>(`${this.apiUrl}/${id}`, { headers: this.getHeaders() });
  }

  // Obtener empleado actual
  obtenerEmpleadoActual(): Observable<Empleado> {
    return this.http.get<Empleado>(`${this.apiUrl}/me`, { headers: this.getHeaders() });
  }

  // Crear empleado
  crear(empleado: CrearEmpleadoDTO): Observable<Empleado> {
    return this.http.post<Empleado>(this.apiUrl, empleado, { headers: this.getHeaders() });
  }

  // Actualizar empleado
  actualizar(id: number, empleado: ActualizarEmpleadoDTO): Observable<Empleado> {
    return this.http.put<Empleado>(`${this.apiUrl}/${id}`, empleado, { headers: this.getHeaders() });
  }

  // Desactivar empleado
  desactivar(id: number): Observable<Empleado> {
    return this.http.post<Empleado>(`${this.apiUrl}/${id}/desactivar`, {}, { headers: this.getHeaders() });
  }

  // Activar empleado
  activar(id: number): Observable<Empleado> {
    return this.http.post<Empleado>(`${this.apiUrl}/${id}/activar`, {}, { headers: this.getHeaders() });
  }

  // Buscar empleados
  buscar(termino: string): Observable<Empleado[]> {
    return this.http.get<Empleado[]>(`${this.apiUrl}/buscar?q=${termino}`, { headers: this.getHeaders() });
  }

  // Obtener empleados activos
  obtenerActivos(): Observable<Empleado[]> {
    return this.http.get<Empleado[]>(`${this.apiUrl}/activos`, { headers: this.getHeaders() });
  }

  // Obtener por rol
  obtenerPorRol(rol: RolEmpleado): Observable<Empleado[]> {
    return this.http.get<Empleado[]>(`${this.apiUrl}/rol/${rol}`, { headers: this.getHeaders() });
  }

  // Obtener por departamento
  obtenerPorDepartamento(departamento: string): Observable<Empleado[]> {
    return this.http.get<Empleado[]>(`${this.apiUrl}/departamento/${departamento}`, { headers: this.getHeaders() });
  }
}
