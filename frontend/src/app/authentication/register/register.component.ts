import { Component, inject } from '@angular/core';
import { FormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';
import { AuthService } from '../../services/auth.service';
import { Router, RouterLink } from '@angular/router';
import { UserRegistration } from '../../models/requests/userRegistration';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-register',
  standalone: true,
  imports: [CommonModule,ReactiveFormsModule, RouterLink],
  templateUrl: './register.component.html',
  styleUrl: './register.component.css'
})
export class RegisterComponent {

  private formBuilder: FormBuilder = inject(FormBuilder);
  private authService: AuthService = inject(AuthService);
  private router: Router = inject(Router);

  constructor() { }

  registerForm = this.formBuilder.group({
    username: ['', Validators.required],
    password: ['', Validators.required],
    firstName: ['', Validators.required],
    lastName: ['', Validators.required],
    gender: ['M', Validators.required],
    address: ['', Validators.required],
    phoneNumber: ['', Validators.required],
    email: ['', [Validators.required, Validators.email]],
    profilePicturePath: [''],
    creditCardNumber: ['', Validators.required],
    userType: ['TOURIST', Validators.required]
  });

  errorMessage: string = '';

  onSubmit() {
    if (this.registerForm.valid) {
      const { username, password } = this.registerForm.value;
      let userRegistration = new UserRegistration();
      userRegistration.username = username ? username : '';
      userRegistration.password = password ? password : '';
      this.authService.register(userRegistration).subscribe({
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
