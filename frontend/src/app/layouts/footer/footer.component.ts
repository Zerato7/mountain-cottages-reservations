import { Component, Input } from '@angular/core';
import { UserType } from '../../models/responses/userResponse';
import { AuthService } from '../../services/auth.service';
import { CommonModule } from '@angular/common';
import { BgColourUtil } from '../../utils/bg-colour.util';

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
    return BgColourUtil.getFooterBgClass(this.authService.getUserType());
  }

}
