import { Routes } from '@angular/router';
import { LoginComponent } from './authentication/login/login.component';
import { RegisterComponent } from './authentication/register/register.component';
import { RegisterSuccessComponent } from './authentication/register-success/register-success.component';
import { PasswordChangeComponent } from './authentication/password-change/password-change.component';

export const routes: Routes = [
  {path: 'admin/login', component: LoginComponent},
  {path: 'login', component: LoginComponent},
  {path: 'register', component: RegisterComponent},
  {path: 'register-success', component: RegisterSuccessComponent},
  {path: 'password-change', component: PasswordChangeComponent}
];
