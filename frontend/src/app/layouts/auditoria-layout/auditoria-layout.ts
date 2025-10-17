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


  async cerrarSesion() {
    await this.authService.signOut();
    this.router.navigate(['/login']);
  }

}
