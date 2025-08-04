import { CommonModule } from '@angular/common';
import { Component, inject } from '@angular/core';
import { FormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';
import { AuthService } from '../../services/auth.service';
import { Router, RouterLink } from '@angular/router';
import { UserLogin } from '../../models/requests/userLogin';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule, RouterLink],
  templateUrl: './login.component.html',
  styleUrl: './login.component.css'
})
export class LoginComponent {

  private formBuilder: FormBuilder = inject(FormBuilder);
  private authService: AuthService = inject(AuthService);
  private router: Router = inject(Router);

  constructor() { }

  loginForm = this.formBuilder.group({
    username: ['', Validators.required],
    password: ['', Validators.required]
  });

  errorMessage: string = '';

  onSubmit() {
    if (this.loginForm.valid) {
      const { username, password } = this.loginForm.value;
      let userLogin = new UserLogin();
      userLogin.username = username ? username : '';
      userLogin.password = password ? password : '';
      this.authService.login(userLogin).subscribe({
        next: nonadmin => {
          this.authService.setUser(nonadmin);
          this.router.navigate(['/']);
        },
        error: err => {
          this.errorMessage = err.message;
        }
      });
    }
  }

}
