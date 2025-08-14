import { Component, inject } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { HeaderComponent } from './layouts/header/header.component';
import { FooterComponent } from './layouts/footer/footer.component';
import { AuthService } from './services/auth.service';
import { CommonModule } from '@angular/common';
import { NavigationService } from './services/navigation.service';
import { BgColourUtil } from './utils/bg-colour.util';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [RouterOutlet, NgbModule, HeaderComponent, FooterComponent, CommonModule],
  templateUrl: './app.component.html',
  styleUrl: './app.component.css'
})
export class AppComponent {
  title = 'Планинска викендица';

  constructor(
    public authService: AuthService,
    private navigationService: NavigationService
  ) { }

  getBackgroundClass(): string {
    return BgColourUtil.getMainBgClass(this.authService.getUserType());
  }

}
