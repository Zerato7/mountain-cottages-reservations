import { Component, inject } from '@angular/core';
import { AbstractControl, AsyncValidatorFn, FormControl, FormGroup, ReactiveFormsModule, ValidationErrors, Validators } from '@angular/forms';
import { AuthService } from '../../services/auth.service';
import { Router, RouterLink } from '@angular/router';
import { UserRegistration } from '../../models/requests/userRegistration';
import { CommonModule } from '@angular/common';
import { UserType } from '../../models/userType';
import { Observable, of } from 'rxjs';


const dinersRegexp = /^((300|301|302|303)\d{12})|((36|38)\d{13})$/;
const masterRegexp = /^(51|52|53|54|55)\d{14}$/;
const visaRegexp = /^(4539|4556|4916|4532|4929|4485|4716)\d{12}$/;

const validImageFormats = ['image/jpeg', 'image/png'];
const min_width = 100, max_width = 300;
const min_height = 100, max_height = 300;

@Component({
  selector: 'app-register',
  standalone: true,
  imports: [CommonModule,ReactiveFormsModule, RouterLink],
  templateUrl: './register.component.html',
  styleUrl: './register.component.css'
})
export class RegisterComponent {

  private authService: AuthService = inject(AuthService);
  private router: Router = inject(Router);

  errorMessage: string = '';
  loading: boolean = false;
  cardType: string | null = null;
  imageName: string | null = null;
  imagePreviewUrl: string | null = null;
  imageSize: number | null = null;

  ngOnInit(): void {
    this.registerForm.get('creditCardNumber')?.valueChanges.subscribe(() => {
      this.cardType =this.getCardType();
    })
  }

  // SyncValidators

  passwordMatchValidator(formGroup: AbstractControl): ValidationErrors | null {
    const password = formGroup.get('password')?.value;
    const confirmPassword = formGroup.get('confirmPassword')?.value;

    return password === confirmPassword ? null : {passwordMismatch: true};
  }

  creditCardNumberValidator(control: AbstractControl): ValidationErrors | null {
    const value = control.value?.toString().replace(/\s+/g, '') || '';
    if (dinersRegexp.test(value) ||
        masterRegexp.test(value) ||
        visaRegexp.test(value)) return null;
    else return {invalidNumber: true};
  }

  // AsyncValidators

  imageAsyncValidator(): AsyncValidatorFn {
    return (control: AbstractControl): Observable<ValidationErrors | null> => {
      const file = control.value as File;
      if (!file) return of(null);

      console.log(file.type);
      if (!validImageFormats.includes(file.type)) return of({invalidType: true});

      return new Observable<ValidationErrors | null>(observer => {
        const reader = new FileReader();
        reader.onload = (event: any) => {
          const image = new Image();
          image.src = reader.result as string;

          image.onload = () => {
            const {width, height} = image;
            if (width < min_width || height < min_height || width > max_width || height > max_height) {
              observer.next({invalidDimensions: true});
            } else {
              observer.next(null);
            }
            observer.complete();
          };

          image.onerror = () => {
            observer.next({invalidType: true});
            observer.complete();
          }
        };

        reader.readAsDataURL(file);
      });
    };
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
    gender: new FormControl<string>('M', {nonNullable: true, validators: Validators.required}),
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
      asyncValidators: [this.imageAsyncValidator()],
      updateOn: 'change'
    }),
    creditCardNumber: new FormControl<string>('', {
      nonNullable: true,
      validators: [
        Validators.required,
        this.creditCardNumberValidator
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
      const { username, password, confirmPassword, firstName, lastName, gender, address, phoneNumber, email, image, creditCardNumber, userType } = this.registerForm.value;
      let userRegistration = new UserRegistration();
      userRegistration.username = username ?? '';
      userRegistration.password = password ?? '';
      userRegistration.email = email ?? '';
      userRegistration.firstName = firstName ?? '';
      userRegistration.lastName = lastName ?? '';
      userRegistration.gender = gender === 'M' || gender === 'Ž' ? gender : 'M';
      userRegistration.address = address ?? '';
      userRegistration.phoneNumber = phoneNumber ?? '';
      userRegistration.creditCardNumber = creditCardNumber ?? '';
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

  // OnFile Selection - image

  onFileSelected(event: Event): void {
    const input = event.target as HTMLInputElement;
    const file = input.files?.[0] ?? null;

    this.registerForm.get('image')?.setValue(file);
    this.registerForm.get('image')?.markAsTouched();
    this.registerForm.get('image')?.updateValueAndValidity();

    this.imageName = file?.name ?? null;
    this.imageSize = file?.size ?? null;
    if (file) {
      const reader = new FileReader();
      reader.onload = e => this.imagePreviewUrl = reader.result as string;
      reader.readAsDataURL(file);
    } else {
      this.imagePreviewUrl = null;
    }
  }

  // CardType getter

  private getCardType(): string | null {
    const value = this.registerForm.get('creditCardNumber')?.value || '';
    if (dinersRegexp.test(value)) return 'diners';
    if (masterRegexp.test(value)) return 'mastercard';
    if (visaRegexp.test(value)) return 'visa';
    return null;
  }

}
