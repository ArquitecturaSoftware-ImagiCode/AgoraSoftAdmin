import { Component } from '@angular/core';
import { Router, RouterLink, RouterOutlet } from '@angular/router';
import { BriefcaseBusiness, ChartBarIcon, CircleDollarSign, Key, LayoutDashboard, LogOut, LucideAngularModule, NotebookText, User, Users } from 'lucide-angular';
import { AuthService } from '../../services/auth.service';

@Component({
  selector: 'app-auditoria-layout',
  imports: [RouterOutlet, RouterLink, LucideAngularModule],
  templateUrl: './auditoria-layout.html',
  styleUrl: './auditoria-layout.css'
})
export class AuditoriaLayout {
    readonly DashboardIcon = LayoutDashboard;
  readonly ProjectIcon = NotebookText;
  readonly ProfileIcon = User;
  readonly ApplicantsIcon = Users;
  readonly PasswordIcon = Key;
  readonly LogoutIcon = LogOut;
  readonly PlansIcon = CircleDollarSign;
  readonly ChatIcon = ChartBarIcon;
  readonly FreelancersIcon = BriefcaseBusiness;

  constructor(private authService: AuthService, private router: Router) {}

  // Método para determinar si una ruta está activa
  isActiveRoute(route: string): boolean {
    return this.router.url === route;
  }

  // Método para obtener las clases CSS del botón según si está activo
  getButtonClasses(route: string): string {
    const baseClasses = "flex mt-3 py-3 px-5 rounded-lg gap-2 transition-all duration-300";
    if (this.isActiveRoute(route)) {
      return `${baseClasses} bg-white text-blue-900`;
    } else {
      return `${baseClasses} text-white hover:text-blue-900 hover:bg-white`;
    }
  }

  async cerrarSesion() {
    await this.authService.signOut();
    this.router.navigate(['/login']);
  }

}
