import { CommonModule } from '@angular/common';
import { Component, inject } from '@angular/core';
import { FormControl, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { AuthService } from '../../services/auth.service';
import { Router, RouterLink } from '@angular/router';
import { UserLogin } from '../../models/requests/userLogin';
import { NonAdminResponse } from '../../models/responses/nonadminResponse';

const adminLoginUrl = 'admin/login';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule, RouterLink],
  templateUrl: './login.component.html',
  styleUrl: './login.component.css'
})
export class LoginComponent {

  private authService: AuthService = inject(AuthService);
  private router: Router = inject(Router);

  ngOnInit(): void {
    this.isAdmin = this.router.url.includes(adminLoginUrl);
  }

  loginForm = new FormGroup({
    username: new FormControl<string>('', {validators: Validators.required}),
    password: new FormControl<string>('', {validators: Validators.required})
  });

  isAdmin: boolean = false;
  errorMessage: string = '';

  onSubmit() {
    if (this.loginForm.valid) {
      const { username, password } = this.loginForm.value;
      let userLogin = new UserLogin();
      userLogin.username = username ? username : '';
      userLogin.password = password ? password : '';

      if (this.isAdmin) {
        this.authService.loginAdmin(userLogin).subscribe({
          next: user => {
            this.authService.setAdmin(user);
            this.router.navigate(['/admin/dashboard']);
          },
          error: err => {
            this.errorMessage = err.message;
          }
        });
      } else {
        this.authService.loginNonadmin(userLogin).subscribe({
          next: user => {
            this.authService.setNonadmin(user);
            this.router.navigate(['/profile']);
          },
          error: err => {
            this.errorMessage = err.message;
          }
        });
      }


    }
  }

}
