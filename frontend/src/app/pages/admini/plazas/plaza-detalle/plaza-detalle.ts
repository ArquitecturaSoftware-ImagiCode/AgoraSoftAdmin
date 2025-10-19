import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ActivatedRoute, RouterModule, Router } from '@angular/router';
import { PlazaService } from '../../../../services/plaza.service';
import { Plaza } from '../../../../models/plaza';

@Component({
  selector: 'app-plaza-detalle',
  standalone: true,
  imports: [CommonModule, RouterModule],
  templateUrl: './plaza-detalle.html',
  styleUrls: ['./plaza-detalle.css'],
})
export class PlazaDetallePage implements OnInit {
  plaza: Plaza | null = null;
  loading = false;
  error = '';

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private plazaService: PlazaService
  ) {}

  ngOnInit(): void {
    const id = Number(this.route.snapshot.paramMap.get('id'));
    if (!id) return;
    this.loading = true;
    interface PartialObserver<T> {
      next?: (value: T) => void;
      error?: (err: any) => void;
      complete?: () => void;
    }

    this.plazaService.obtenerPorId(id).subscribe({
      next: (p: Plaza) => {
        this.plaza = p;
        this.loading = false;
      },
      error: (_: any) => {
        this.error = 'No se pudo cargar la plaza';
        this.loading = false;
      },
    } satisfies PartialObserver<Plaza>);
  }

  volver(): void {
    this.router.navigate(['/admin/plazas']);
  }
  aprobar(): void {
    if (this.plaza) this.router.navigate(['/admin/plazas', this.plaza.id, 'aprobar']);
  }
}
