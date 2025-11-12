import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule, Router } from '@angular/router';
import { DashboardService } from '../../../services/dashboard.service';
import { catchError, of } from 'rxjs';

@Component({
  selector: 'app-admin-dashboard',
  standalone: true,
  imports: [CommonModule, RouterModule],
  templateUrl: './admin-dashboard.html',
  styleUrls: ['./admin-dashboard.css'],
})
export class AdminDashboard implements OnInit {
  loading = false;
  error = '';
  data: any;

  constructor(private dashboardService: DashboardService, private router: Router) {}

  ngOnInit(): void {
    this.loading = true;
    this.dashboardService
      .obtenerEstadisticas()
      .pipe(
        catchError((err) => {
          console.error(err);
          this.error = 'No se pudieron cargar las estadísticas';
          this.loading = false;
          return of(null);
        })
      )
      .subscribe((res) => {
        this.data = res;
        this.loading = false;
      });
  }

  ir(ruta: string): void {
    this.router.navigate([ruta]);
  }
}
