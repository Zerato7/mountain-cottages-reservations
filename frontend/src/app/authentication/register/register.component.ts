import { Component, inject } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { AuthService } from '../../services/auth.service';
import { Router, RouterLink } from '@angular/router';
import { UserRegistration } from '../../models/requests/userRegistration';
import { CommonModule } from '@angular/common';
import { UserType } from '../../models/userType';

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

  passwordMatchValidator(formGroup: FormGroup) {
    const password = formGroup.get('password')?.value;
    const confirmPassword = formGroup.get('confirmPassword')?.value;
    return password === confirmPassword ? null : {passwordMismatch: true}
  }

  registerForm = this.formBuilder.group({
    username: ['', Validators.required],
    password: ['', [
      Validators.required,
      Validators.pattern(
        '^(?=(.*[a-z]){3})(?=.*[A-Z])(?=.*\\d)(?=.*[!@#$%^&*])[a-zA-Z][a-zA-Z\\d!@#$%^&*]{5,9}$'
      )]
    ],
    confirmPassword: ['', Validators.required],
    firstName: ['', Validators.required],
    lastName: ['', Validators.required],
    gender: ['M', Validators.required],
    address: ['', Validators.required],
    phoneNumber: ['', [
      Validators.required,
      Validators.pattern('^\\+?[0-9]{10,15}$')
      ]
    ],
    email: ['', [Validators.required, Validators.email]],
    profilePicturePath: [''],
    creditCardNumber: ['', Validators.required],
    userType: ['TOURIST', Validators.required]
  }, {
    validators: this.passwordMatchValidator
  });

  errorMessage: string = '';
  loading: boolean = false;

  onSubmit() {
    if (this.registerForm.valid) {
      this.loading = true;
      const { username, password, confirmPassword, email, firstName, lastName, gender, address, phoneNumber, profilePicturePath, creditCardNumber, userType } = this.registerForm.value;
      let userRegistration = new UserRegistration();
      userRegistration.username = username ? username : '';
      userRegistration.password = password ? password : '';
      userRegistration.email = email ? email : '';
      userRegistration.firstName = firstName ? firstName : '';
      userRegistration.lastName = lastName ? lastName : '';
      userRegistration.gender = gender === 'M' || gender === 'Ž' ? gender : 'M';
      userRegistration.address = address ? address : '';
      userRegistration.phoneNumber = phoneNumber ? phoneNumber : '';
      userRegistration.profilePicturePath = profilePicturePath ? profilePicturePath : '';
      userRegistration.creditCardNumber = creditCardNumber ? creditCardNumber : '';
      userRegistration.userType = userType ? userType as UserType : 'TOURIST' as UserType;

      this.authService.register(userRegistration).subscribe({
        next: message => {
          this.loading = false;
          this.router.navigate(['/register-success'], {
            state: { message: message }
          });
        },
        error: err => {
          this.loading = false;
          this.errorMessage = err.message;
        }
      });
    }
  }

}
