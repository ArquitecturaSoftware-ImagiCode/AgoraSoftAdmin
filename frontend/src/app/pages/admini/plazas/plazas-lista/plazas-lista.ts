import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule, Router } from '@angular/router';
import { ReactiveFormsModule, FormControl } from '@angular/forms';
import { PlazaService } from '../../../../services/plaza.service';
import { Plaza } from '../../../../models/plaza';
import { debounceTime, switchMap, catchError, of } from 'rxjs';

@Component({
  selector: 'app-plazas-lista',
  standalone: true,
  imports: [CommonModule, RouterModule, ReactiveFormsModule],
  templateUrl: './plazas-lista.html',
  styleUrls: ['./plazas-lista.css'],
})
export class PlazasListaPage implements OnInit {
  plazas: Plaza[] = [];
  loading = false;
  error = '';
  search = new FormControl('', { nonNullable: true });

  constructor(private plazaService: PlazaService, private router: Router) {}

  ngOnInit(): void {
    this.cargar();
    this.search.valueChanges
      .pipe(
        debounceTime(300),
        switchMap((q) => (q ? this.plazaService.buscar(q) : this.plazaService.obtenerTodas())),
        catchError((err) => {
          console.warn(err);
          this.error = 'Error en la búsqueda';
          return of([]);
        })
      )
      .subscribe((l) => (this.plazas = l || []));
  }

  cargar(): void {
    this.loading = true;
    this.plazaService.obtenerTodas().subscribe({
      next: (res: Plaza[]): void => {
        this.plazas = res;
        this.loading = false;
      },
      error: (_: unknown): void => {
        this.error = 'No se pudieron cargar plazas';
        this.loading = false;
      },
    });
  }

  ver(id: number): void {
    this.router.navigate(['/admin/plazas', id]);
  }
  aprobar(id: number): void {
    this.router.navigate(['/admin/plazas', id, 'aprobar']);
  }
}
