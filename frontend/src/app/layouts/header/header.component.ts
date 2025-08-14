import { CommonModule } from '@angular/common';
import { Component, inject, Input } from '@angular/core';
import { Router, RouterLink, RouterLinkActive } from '@angular/router';
import { AuthService } from '../../services/auth.service';
import { UserType } from '../../models/userType';
import { BgColourUtil } from '../../utils/bg-colour.util';

@Component({
  selector: 'app-header',
  standalone: true,
  imports: [RouterLink, CommonModule, RouterLinkActive],
  templateUrl: './header.component.html',
  styleUrl: './header.component.css'
})
export class HeaderComponent {

  constructor(
    private authService:  AuthService,
    private router: Router
  ) { }

  @Input() userRole: UserType | null = null;

  logout() {
    console.log('User logged out');
    this.authService.logout();
    this.router.navigate(['/login']);
  }

  isAuthPage(): boolean {
    return this.authService.isAuthPage(this.router.url);
  }

  isPasswordChangePage(): boolean {
    return this.router.url === '/password-change';
  }

  getBackgroundClass(): string {
    return BgColourUtil.getHeaderBgClass(this.authService.getUserType());
  }

}
