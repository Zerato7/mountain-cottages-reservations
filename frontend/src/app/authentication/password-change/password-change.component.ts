import { Component } from '@angular/core';
import { AbstractControl, FormControl, FormGroup, ReactiveFormsModule, ValidationErrors, Validators } from '@angular/forms';
import { AuthService } from '../../services/auth.service';
import { Router } from '@angular/router';
import { CommonModule } from '@angular/common';
import { PasswordChange } from '../../models/requests/passwordChange';
import { NavigationService } from '../../services/navigation.service';

@Component({
  selector: 'app-password-change',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule],
  templateUrl: './password-change.component.html',
  styleUrl: './password-change.component.css'
})
export class PasswordChangeComponent {

  userId: Number = 0

  constructor(
    private authService: AuthService,
    private navigationService: NavigationService,
    private router: Router
  ) {}

  newPasswordValidator(formGroup: AbstractControl): ValidationErrors | null {
    const currentPassword = formGroup.get('currentPassword')?.value;
    const newPassword = formGroup.get('newPassword')?.value;
    const confirmNewPassword = formGroup.get('confirmNewPassword')?.value;

    const errors: ValidationErrors = {}

    if (newPassword !== confirmNewPassword) {
      errors['passwordsMismatch'] = true;
    }

    if (newPassword === currentPassword) {
      errors['currentNewPasswordsSame'] = true;
    }

    return Object.keys(errors).length ? errors : null;
  }

  passwordChangeForm = new FormGroup({
    currentPassword: new FormControl<string>('', {nonNullable: true, validators: Validators.required}),
    newPassword: new FormControl<string>('', {
      nonNullable: true,
      validators: [
        Validators.required,
        Validators.pattern(
          '^(?=(.*[a-z]){3})(?=.*[A-Z])(?=.*\\d)(?=.*[!@#$%^&*])[a-zA-Z][a-zA-Z\\d!@#$%^&*]{5,9}$'
        )
      ]
    }),
    confirmNewPassword: new FormControl<string>('', {nonNullable: true, validators: Validators.required})
  }, {
    validators: this.newPasswordValidator
  });

  errorMessage: string = ''

  onSubmit() {
    if (this.passwordChangeForm.valid) {
      const {currentPassword, newPassword, confirmNewPassword}  = this.passwordChangeForm.value;
      let passwordChange: PasswordChange = new PasswordChange();
      passwordChange.id = this.authService.getUser()?.id || 0;
      passwordChange.currentPassword = currentPassword ?? '';
      passwordChange.newPassword = newPassword ?? '';
      this.authService.changePassword(passwordChange).subscribe({
        next: message => {
          this.router.navigate(['/login']);
        },
        error: err => {
          this.errorMessage = err.message;
        }
      });
    }
  }

  back() {
    this.router.navigate([this.navigationService.getPreviousUrl()]);
  }

}
