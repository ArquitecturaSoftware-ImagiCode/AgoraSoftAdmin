import { Component, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule, Router } from '@angular/router';
import { LucideAngularModule } from 'lucide-angular';
// Si usas name="log-out", registra el icono con pick:
import { LogOut } from 'lucide-angular';

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
  LogoutIcon = LogOut;

  cerrarSesion(): void {
    localStorage.removeItem('clerk_token');
    localStorage.removeItem('user');
    try {
      // @ts-ignore
      if (typeof (window as any).Clerk?.signOut === 'function') {
        // @ts-ignore
        (window as any).Clerk.signOut();
      }
    } catch (e) {
      console.warn('Clerk signOut falló', e);
    }
    this.router.navigate(['/login']).catch(() => (window.location.href = '/login'));
  }
}
