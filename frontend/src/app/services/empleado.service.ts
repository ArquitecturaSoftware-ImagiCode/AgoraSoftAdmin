import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../environments/environments';

export interface Empleado {
  id?: number;
  nombre: string;
  apellido: string;
  correo: string;
  telefono?: string;
  rol: string;
  activo?: boolean;
  fechaIngreso?: string;
}

@Injectable({ providedIn: 'root' })
export class EmpleadoService {
  private readonly baseUrl = `${environment.apiBaseUrl}/empleado`;

  constructor(private http: HttpClient) {}

  getEmpleados(): Observable<Empleado[]> {
    return this.http.get<Empleado[]>(`${this.baseUrl}`);
  }

  crearEmpleado(payload: Partial<Empleado>): Observable<Empleado> {
    return this.http.post<Empleado>(`${this.baseUrl}`, payload);
  }
}


