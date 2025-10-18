import { Component, Input } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Location } from '@angular/common';

@Component({
  selector: 'app-header',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './header.html',
  styleUrls: ['./header.css'],
})
export class HeaderComponent {
  @Input() title = '';
  @Input() subtitle = '';
  @Input() showBackButton = false;

  currentDate = new Date();

  constructor(private location: Location) {
    setInterval(() => (this.currentDate = new Date()), 60_000);
  }

  goBack(): void {
    this.location.back();
  }

  getFormattedDate(): string {
    return this.currentDate.toLocaleDateString('es-CL', {
      weekday: 'long',
      day: 'numeric',
      month: 'long',
      year: 'numeric',
    });
  }

  getFormattedTime(): string {
    return this.currentDate.toLocaleTimeString('es-CL', { hour: '2-digit', minute: '2-digit' });
  }
}
