import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ReactiveFormsModule, FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router, RouterModule } from '@angular/router';
import { PlazaService } from '../../../services/plaza.service';
import { Plaza } from '../../../models/plaza.model';
import { catchError, of } from 'rxjs';

@Component({
  selector: 'app-plaza-aprobacion',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule, RouterModule],
  templateUrl: './plaza-aprobacion.html',
  styleUrls: ['./plaza-aprobacion.css']
})
export class PlazaAprobacionComponent implements OnInit {
  plaza: Plaza | null = null;
  loading = false;

  form!: FormGroup;
  motivoForm!: FormGroup;

  constructor(
    private route: ActivatedRoute,
    private plazaService: PlazaService,
    private fb: FormBuilder,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.form = this.fb.group({
      planInicial: ['', Validators.required],
      montoMensual: [0, [Validators.required, Validators.min(0)]],
      metodoPago: ['']
    });

    this.motivoForm = this.fb.group({
      motivo: ['', Validators.required]
    });

    const id = this.route.snapshot.paramMap.get('id');
    if (id) this.cargarPlaza(+id);
  }

  cargarPlaza(id: number): void {
    this.loading = true;
    this.plazaService.obtenerPorId(id)
      .pipe(
        catchError(err => {
          console.error('Error cargando plaza:', err);
          this.loading = false;
          return of(null);
        })
      )
      .subscribe({
        next: p => { if (p) this.plaza = p; this.loading = false; }
      });
  }

  aprobar(): void {
    if (!this.plaza || this.form.invalid) return;
    this.plazaService.aprobar(this.plaza.id, this.form.value).subscribe({
      next: () => this.router.navigate(['/admin/plazas']),
      error: err => { console.error(err); alert('Error aprobando plaza'); }
    });
  }

  rechazar(): void {
    if (!this.plaza || this.motivoForm.invalid) return;
    this.plazaService.rechazar(this.plaza.id, this.motivoForm.value).subscribe({
      next: () => this.router.navigate(['/admin/plazas']),
      error: err => { console.error(err); alert('Error rechazando plaza'); }
    });
  }

  suspender(): void {
    if (!this.plaza) return;
    const motivo = prompt('Motivo de la suspensión:') || '';
    if (!motivo) return;
    this.plazaService.suspender(this.plaza.id, { motivo }).subscribe({
      next: () => this.router.navigate(['/admin/plazas']),
      error: err => { console.error(err); alert('Error suspendiendo plaza'); }
    });
  }
}