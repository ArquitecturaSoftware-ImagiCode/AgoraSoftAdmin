import { SidebarComponent } from '../sidebar/sidebar';
import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
// Update the import path to the correct relative location of SidebarComponent


@Component({
  selector: 'app-admin-layout',
  standalone: true,
  imports: [CommonModule, RouterModule, SidebarComponent],
  template: `
    <div class="admin-layout">
      <app-sidebar></app-sidebar>
      <main class="main-content">
        <router-outlet></router-outlet>
      </main>
    </div>
  `,
  styles: [`
    .admin-layout { display:flex; min-height:100vh; background:#f3f4f6; }
    .main-content { flex:1; margin-left:280px; padding:24px 32px; min-height:100vh; }
  `]
})
export class AdminLayoutComponent {}
