import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ReactiveFormsModule, FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute, Router, RouterModule } from '@angular/router';
import { PlazaService } from '../../../services/plaza.service';
import { Plaza } from '../../../models/plaza.model';

@Component({
  selector: 'app-plaza-aprobacion',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule, RouterModule],
  templateUrl: './plaza-aprobacion.html',
  styleUrls: ['./plaza-aprobacion.css']
})
export class PlazaAprobacionComponent implements OnInit {
    constructor(private route: ActivatedRoute, private plazaService: PlazaService, private fb: FormBuilder, private router: Router) {}
  plaza: Plaza | null = null;
  loading = false;
  form = this.fb.group({
    planInicial: ['', Validators.required],
    montoMensual: [0, [Validators.required, Validators.min(0)]],
    metodoPago: ['']
  });
  motivoForm = this.fb.group({ motivo: ['', Validators.required] });



  ngOnInit(): void {
    const id = this.route.snapshot.paramMap.get('id');
    if (id) this.cargarPlaza(+id);
  }

  cargarPlaza(id: number): void {
    this.loading = true;
    this.plazaService.obtenerPorId(id).subscribe({
      next: p => { this.plaza = p; this.loading = false; },
      error: err => { console.error(err); this.loading = false; }
    });
  }

  aprobar(): void {
    if (!this.plaza || this.form.invalid) return;
    const dto = {
      planInicial: this.form.value.planInicial ?? '',
      montoMensual: Number(this.form.value.montoMensual ?? 0),
      metodoPago: this.form.value.metodoPago ?? ''
    };
    this.plazaService.aprobar(this.plaza.id, dto).subscribe({
      next: () => this.router.navigate(['/admin/plazas']),
      error: err => { console.error(err); alert('Error aprobando plaza'); }
    });
  }

  rechazar(): void {
    if (!this.plaza || this.motivoForm.invalid) return;
    const motivo = this.motivoForm.value.motivo ?? '';
    this.plazaService.rechazar(this.plaza.id, { motivo }).subscribe({
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
