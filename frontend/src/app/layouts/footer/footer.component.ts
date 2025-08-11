import { Component, Input } from '@angular/core';
import { UserType } from '../../models/userType';
import { AuthService } from '../../services/auth.service';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-footer',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './footer.component.html',
  styleUrl: './footer.component.css'
})
export class FooterComponent {

  constructor(
    private authService: AuthService
  ) { }

  @Input() userRole: UserType | null = null;

  getBackgroundClass(): string {
    switch (this.authService.getUserType()) {
      case UserType.TOURIST: return 'bg-footer-tourist';
      case UserType.HOST: return 'bg-footer-host';
      case UserType.ADMIN: return 'bg-footer-admin';
      default: return 'bg-footer-unregistered';
    }
  }

}
