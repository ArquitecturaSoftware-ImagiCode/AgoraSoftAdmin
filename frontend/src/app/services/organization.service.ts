import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../environments/environments';


export interface Organization {
  id: number;
  clerkOrgId: string;
  nombre: string;
  activo: boolean;
  createdAt: string;
  updatedAt: string | null;
}

@Injectable({
  providedIn: 'root'
})
export class OrganizationService {
  private readonly apiUrl = `${environment.apiBaseUrl}/admin/organizations`;

  constructor(private http: HttpClient) {}

  private getHeaders(token: string): HttpHeaders {
    return new HttpHeaders({
      'Authorization': `Bearer ${token}`,
      'Content-Type': 'application/json'
    });
  }

  // GET: todas las organizaciones
  getAll(token: string): Observable<Organization[]> {
    return this.http.get<Organization[]>(`${this.apiUrl}/all`, { headers: this.getHeaders(token) });
  }

  // GET: organización por ID
  getById(id: number, token: string): Observable<Organization> {
    return this.http.get<Organization>(`${this.apiUrl}/${id}`, { headers: this.getHeaders(token) });
  }

  // GET: organización por ClerkOrgId
  getByClerkOrgId(clerkOrgId: string, token: string): Observable<Organization> {
    return this.http.get<Organization>(`${this.apiUrl}/clerk/${clerkOrgId}`, { headers: this.getHeaders(token) });
  }

  // POST: crear organización
  create(org: Partial<Organization>, token: string): Observable<Organization> {
    return this.http.post<Organization>(this.apiUrl, org, { headers: this.getHeaders(token) });
  }

  // PUT: actualizar organización
  update(id: number, org: Partial<Organization>, token: string): Observable<Organization> {
    return this.http.put<Organization>(`${this.apiUrl}/${id}`, org, { headers: this.getHeaders(token) });
  }

  // POST: cambiar estado activo/inactivo
  toggleEstado(id: number, activo: boolean, token: string): Observable<Organization> {
    return this.http.post<Organization>(
      `${this.apiUrl}/${id}/estado?activo=${activo}`,
      {},
      { headers: this.getHeaders(token) }
    );
  }

  // DELETE: eliminar organización
  delete(id: number, token: string): Observable<{ mensaje: string }> {
    return this.http.delete<{ mensaje: string }>(`${this.apiUrl}/${id}`, { headers: this.getHeaders(token) });
  }
}
