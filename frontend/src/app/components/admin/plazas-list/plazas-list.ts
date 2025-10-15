import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Router, RouterModule } from '@angular/router';
import { ReactiveFormsModule, FormControl } from '@angular/forms';
import { catchError, debounceTime, switchMap, of } from 'rxjs';
import { PlazaService } from '../../../services/plaza.service';
import { Plaza, EstadoPlaza } from '../../../models/plaza.model';

@Component({
  selector: 'app-plazas-list',
  standalone: true,
  imports: [CommonModule, RouterModule, ReactiveFormsModule],
  templateUrl: './plazas-list.html',
  styleUrls: ['./plazas-list.css']
})
export class PlazasListComponent implements OnInit {
  plazas: Plaza[] = [];
  loading = false;
  error = '';
  searchControl = new FormControl('');
  estados = Object.values(EstadoPlaza);

  constructor(private plazaService: PlazaService, private router: Router) {}

  ngOnInit(): void {
    this.cargarPlazas();

    this.searchControl.valueChanges.pipe(
      debounceTime(300),
      switchMap(term => {
        if (!term) return this.plazaService.obtenerTodas();
        return this.plazaService.buscar(term);
      }),
      catchError(err => { console.warn(err); this.error = 'Error en búsqueda'; return of([]); })
    ).subscribe((res: Plaza[] | any) => this.plazas = Array.isArray(res) ? res : []);
  }

  cargarPlazas(): void {
    this.loading = true;
    this.plazaService.obtenerTodas().subscribe({
      next: res => { this.plazas = res; this.loading = false; },
      error: err => { console.error(err); this.error = 'No se pudieron cargar plazas'; this.loading = false; }
    });
  }

  verPlaza(id: number): void { this.router.navigate(['/admin/plazas', id]); }
  editarPlaza(id: number): void { this.router.navigate(['/admin/plazas', id, 'editar']); }
  abrirAprobacion(id: number): void { this.router.navigate(['/admin/plazas', id, 'aprobar']); }
}
