import { Component } from '@angular/core';
import { AbstractControl, FormControl, FormGroup, ReactiveFormsModule, ValidationErrors, Validators } from '@angular/forms';
import { AuthService } from '../../services/auth.service';
import { Router, RouterLink } from '@angular/router';
import { UserRegistration } from '../../models/requests/userRegistration';
import { CommonModule } from '@angular/common';
import { UserType } from '../../models/userType';
import { CreditCardService } from '../../services/credit-card.service';
import { ImageService } from '../../services/image.service';

@Component({
  selector: 'app-register',
  standalone: true,
  imports: [CommonModule,ReactiveFormsModule, RouterLink],
  templateUrl: './register.component.html',
  styleUrl: './register.component.css'
})
export class RegisterComponent {

  constructor(
    private authService: AuthService,
    private creditCardService: CreditCardService,
    private imageService: ImageService,
    private router: Router
  ) { }

  errorMessage: string = '';
  loading: boolean = false;
  cardType: string | null = null;

  ngOnInit(): void {
    this.registerForm.get('creditCardNumber')?.valueChanges.subscribe(() => {
      this.cardType =this.creditCardService.getCardType(
        this.registerForm.get('creditCardNumber')?.value || ''
      );
    });
  }

  // SyncValidators

  passwordMatchValidator(formGroup: AbstractControl): ValidationErrors | null {
    const password = formGroup.get('password')?.value;
    const confirmPassword = formGroup.get('confirmPassword')?.value;

    return password === confirmPassword ? null : {passwordMismatch: true};
  }

  // FormGroup

  registerForm = new FormGroup({
    username: new FormControl<string>('', {nonNullable: true, validators: Validators.required}),
    password: new FormControl<string>('', {
      nonNullable: true,
      validators: [
        Validators.required,
        Validators.pattern(
          '^(?=(.*[a-z]){3})(?=.*[A-Z])(?=.*\\d)(?=.*[!@#$%^&*])[a-zA-Z][a-zA-Z\\d!@#$%^&*]{5,9}$'
        )
      ]
    }),
    confirmPassword: new FormControl<string>('', {nonNullable: true, validators: Validators.required}),
    firstName: new FormControl<string>('', {nonNullable: true, validators: Validators.required}),
    lastName: new FormControl<string>('', {nonNullable: true, validators: Validators.required}),
    address: new FormControl<string>('', {nonNullable: true, validators: Validators.required}),
    gender: new FormControl<string>('М', {nonNullable: true, validators: Validators.required}),
    phoneNumber: new FormControl<string>('', {
      nonNullable: true,
      validators: [
        Validators.required,
        Validators.pattern('^\\+?[0-9]{10,15}$')
      ]
    }),
    email: new FormControl<string>('', {
      nonNullable: true,
      validators: [
        Validators.required,
        Validators.email
      ]
    }),
    image: new FormControl<File|null>(null, {
      validators: [],
      asyncValidators: [ImageService.imageAsyncValidator()],
      updateOn: 'change'
    }),
    creditCardNumber: new FormControl<string>('', {
      nonNullable: true,
      validators: [
        Validators.required,
        CreditCardService.creditCardNumberValidator
      ]
    }),
    userType: new FormControl<UserType>(UserType.TOURIST, {nonNullable:true, validators: Validators.required})
  }, {
    validators: this.passwordMatchValidator
  });

  // OnSumbit Form

  onSubmit(): void {
    if (this.registerForm.valid) {
      this.loading = true;
      const { username, password, confirmPassword, firstName, lastName, address, gender, phoneNumber, email, image, creditCardNumber, userType } = this.registerForm.value;
      let userRegistration = new UserRegistration();
      userRegistration.username = username ?? '';
      userRegistration.password = password ?? '';
      userRegistration.email = email ?? '';
      userRegistration.firstName = firstName ?? '';
      userRegistration.lastName = lastName ?? '';
      userRegistration.address = address ?? '';
      userRegistration.gender = gender === 'М' || gender === 'Ж' ? gender : 'М';
      userRegistration.phoneNumber = phoneNumber ?? '';
      userRegistration.creditCardNumber = this.creditCardService.trim(creditCardNumber ?? '');
      userRegistration.userType = userType ?? UserType.TOURIST;

      const formData = new FormData();
      formData.append('user',
        new Blob([JSON.stringify(userRegistration)], {type: 'application/json'})
      );

      if (image) formData.append('profilePicture', image);

      this.authService.register(formData).subscribe({
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

  // Image

  onFileSelected(event: Event): void {
    this.imageService.onFileSelected(event, this.registerForm, 'image');
  }

  getImageName(): string | null {
    return this.imageService.getImageName();
  }

  getImageSize(): number {
    return this.imageService.getImageSize() ?? 0;
  }

  getImagePreviewUrl(): string | null {
    return this.imageService.getImagePreviewUrl();
  }

}
