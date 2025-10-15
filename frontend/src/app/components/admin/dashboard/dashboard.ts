import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { HeaderComponent } from '../header/header';
import { DashboardService } from '../../services/dashboard.service';
import { DashboardEstadisticas } from '../../models/dashboard.model';
import { RouterModule, Router } from '@angular/router';
import { catchError, of } from 'rxjs';
import { Heater } from 'lucide-angular';

@Component({
  selector: 'app-dashboard',
  standalone: true,
  imports: [CommonModule, RouterModule, HeaderComponent],
  templateUrl: './dashboard.html',
  styleUrls: ['./dashboard.css']
})
export class DashboardComponent implements OnInit {
  estadisticas: DashboardEstadisticas | null = null;
  loading = true;
  error = '';

  constructor(private dashboardService: DashboardService, private router: Router) {}

  ngOnInit(): void { this.cargarEstadisticas(); }

  cargarEstadisticas(): void {
    this.loading = true;
    this.dashboardService.obtenerEstadisticas()
      .pipe(catchError(err => { this.error = 'No se pudieron cargar las estadísticas.'; this.loading = false; return of(null); }))
      .subscribe(data => {
        this.estadisticas = data;
        this.loading = false;
      });
  }

  navegarA(ruta: string) { this.router.navigate([ruta]); }

  get porcentajePlazasActivas(): number {
    if (!this.estadisticas || this.estadisticas.totalPlazas === 0) return 0;
    return (this.estadisticas.plazasActivas / this.estadisticas.totalPlazas) * 100;
  }

  get porcentajeEmpleadosActivos(): number {
    if (!this.estadisticas || this.estadisticas.totalEmpleados === 0) return 0;
    return (this.estadisticas.empleadosActivos / this.estadisticas.totalEmpleados) * 100;
  }

  formatCurrency(value = 0): string {
    return new Intl.NumberFormat('es-CL', { style: 'currency', currency: 'CLP' }).format(value);
  }
}
