import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ReactiveFormsModule, FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, RouterModule, Router } from '@angular/router';
import { PlazaService } from '../../../../services/plaza.service';

@Component({
  selector: 'app-plaza-aprobacion-page',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule, RouterModule],
  templateUrl: './plaza-aprobacion.html',
  styleUrls: ['./plaza-aprobacion.css'],
})
export class PlazaAprobacionPage implements OnInit {
  form!: FormGroup;
  motivoForm!: FormGroup;
  id!: number;

  constructor(
    private fb: FormBuilder,
    private route: ActivatedRoute,
    private router: Router,
    private plazaService: PlazaService
  ) {}

  ngOnInit(): void {
    this.id = Number(this.route.snapshot.paramMap.get('id'));
    this.form = this.fb.group({
      planInicial: ['', Validators.required],
      montoMensual: [0, [Validators.required, Validators.min(0)]],
      metodoPago: [''],
    });
    this.motivoForm = this.fb.group({ motivo: ['', Validators.required] });
  }

  aprobar(): void {
    if (this.form.invalid) return;
    interface AprobacionPayload {
      planInicial: string;
      montoMensual: number;
      metodoPago: string;
    }
    this.plazaService.aprobar(this.id, this.form.value as AprobacionPayload).subscribe({
      next: (_: unknown): void => {
        this.router.navigate(['/admin/plazas']);
      },
      error: (_: unknown): void => alert('Error aprobando plaza'),
    });
  }

  rechazar(): void {
    if (this.motivoForm.invalid) return;
    interface RechazoPayload {
      motivo: string;
    }
    this.plazaService.rechazar(this.id, this.motivoForm.value as RechazoPayload).subscribe({
      next: () => {
        this.router.navigate(['/admin/plazas']);
      },
      error: (_: unknown): void => alert('Error rechazando plaza'),
    });
  }

  suspender(): void {
    const motivo = prompt('Motivo de la suspensión:') || '';
    if (!motivo) return;
    interface SuspenderPayload {
      motivo: string;
    }

    this.plazaService.suspender(this.id, { motivo } as SuspenderPayload).subscribe({
      next: (): void => {
        this.router.navigate(['/admin/plazas']);
      },
      error: (_: unknown): void => alert('Error suspendiendo plaza'),
    });
  }
}
