import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Subscription } from '../models/Subscription';
import { environment } from '../../environments/environments';

@Injectable({
  providedIn: 'root'
})
export class SubscriptionService {
  private apiUrl = `${environment.apiBaseUrl}/subscriptions`;

  constructor(private http: HttpClient) {}

  // GET: traer todas las suscripciones
  getSubscriptions(): Observable<Subscription[]> {
    return this.http.get<Subscription[]>(this.apiUrl);
  }

  // GET: traer suscripciones activas
  getActiveSubscriptions(): Observable<Subscription[]> {
    return this.http.get<Subscription[]>(`${this.apiUrl}/active`);
  }

  // GET: traer suscripciones por organización
  getSubscriptionsByOrganization(organizationId: string): Observable<Subscription[]> {
    return this.http.get<Subscription[]>(`${this.apiUrl}/organization/${organizationId}`);
  }

  // GET: traer suscripciones que expiran pronto
  getExpiringSoonSubscriptions(): Observable<Subscription[]> {
    return this.http.get<Subscription[]>(`${this.apiUrl}/expiring-soon`);
  }
}
