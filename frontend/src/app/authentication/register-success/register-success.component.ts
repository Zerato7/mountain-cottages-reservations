import { CommonModule } from '@angular/common';
import { Component, inject } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-register-success',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './register-success.component.html',
  styleUrl: './register-success.component.css'
})
export class RegisterSuccessComponent {

  private router = inject(Router);

  successMessage: string = 'Захтев успешно послат!';

  ngOnInit() {
    const navigation = this.router.getCurrentNavigation();
    if (navigation && navigation.extras.state) {
      this.successMessage = navigation.extras.state['message'] || 'Захтев успешно послат!';
    }
  }

  goToLogin() {
    this.router.navigate(['/login']);
  }

}
