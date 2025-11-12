import { Component, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule, Router } from '@angular/router';
import { LogOut, LucideAngularModule } from 'lucide-angular';
// Si usas name="log-out", registra el icono con pick:
import { AuthService } from '../../services/auth.service';

@Component({
  selector: 'app-admin-layout',
  standalone: true,
  // pick registra solo los iconos que uses por nombre
  imports: [CommonModule, RouterModule, LucideAngularModule],
  templateUrl: './admin-layout.html',
  styleUrls: ['./admin-layout.css'],
})
export class AdminLayoutComponent {
  private router = inject(Router);
  private authService = inject(AuthService);

  readonly LogoutIcon = LogOut;
  async cerrarSesion(): Promise<void> {
    try {
      // delega al servicio de auth (debe manejar limpieza de sesión/token)
      if (this.authService && typeof this.authService.signOut === 'function') {
        await this.authService.signOut();
      }

      // limpieza adicional local (no dañará si ya fue limpiado por el servicio)
      localStorage.removeItem('clerk_token');
      localStorage.removeItem('user');

      // redirige al login
      await this.router.navigate(['/login']);
    } catch (e) {
      console.warn('Cerrar sesión falló, se fuerza navegación a /login', e);
      // fallback
      this.router.navigate(['/login']).catch(() => (window.location.href = '/login'));
    }
  }
}
