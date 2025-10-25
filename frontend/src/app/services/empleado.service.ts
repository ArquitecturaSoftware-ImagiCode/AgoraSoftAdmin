import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Empleado, CrearEmpleadoDTO, ActualizarEmpleadoDTO, RolEmpleado } from '../models/empleado'; 
import { environment } from '../../environments/environments';
import { AuthService } from './auth.service';

@Injectable({
  providedIn: 'root'
})
export class EmpleadoService {
  private apiUrl = `${environment.apiBaseUrl}/admin/empleados`;

  constructor(private http: HttpClient, private authService: AuthService) {}

  crearEmpleado(empleado: Empleado, token?: string): Observable<Empleado> {
  let headers = new HttpHeaders({ 'Content-Type': 'application/json' });
  if (token) {
    headers = headers.set('Authorization', `Bearer ${token}`);
  }
  return this.http.post<Empleado>(this.apiUrl, empleado, { headers });
}

getEmpleados(token?: string): Observable<Empleado[]> {
  let headers = new HttpHeaders();
  if (token) headers = headers.set('Authorization', `Bearer ${token}`);
  return this.http.get<Empleado[]>(this.apiUrl, { headers });
}
}
