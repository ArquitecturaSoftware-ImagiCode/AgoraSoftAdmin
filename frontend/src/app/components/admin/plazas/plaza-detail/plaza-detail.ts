import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ActivatedRoute, Router, RouterModule } from '@angular/router';
import { PlazaService } from '../../../../services/plaza.service';
import { Plaza } from '../../../../models/plaza';

@Component({
  selector: 'app-plaza-detail',
  standalone: true,
  imports: [CommonModule, RouterModule],
  templateUrl: './plaza-detail.html',
  styleUrls: ['./plaza-detail.css'],
})
export class PlazaDetailComponent implements OnInit {
  plaza: Plaza | null = null;
  loading = false;
  error = '';

  constructor(
    private route: ActivatedRoute,
    private plazaService: PlazaService,
    private router: Router
  ) {}

  ngOnInit(): void {
    const id = this.route.snapshot.paramMap.get('id');
    if (id) this.cargarPlaza(+id);
  }

  cargarPlaza(id: number): void {
    this.loading = true;
    this.plazaService.obtenerPorId(id).subscribe({
      next: (p) => {
        this.plaza = p;
        this.loading = false;
      },
      error: (err) => {
        console.error(err);
        this.error = 'No se pudo cargar plaza';
        this.loading = false;
      },
    });
  }

  volver(): void {
    this.router.navigate(['/admin/plazas']);
  }
  aprobar(): void {
    if (this.plaza) this.router.navigate(['/admin/plazas', this.plaza.id, 'aprobar']);
  }
}
