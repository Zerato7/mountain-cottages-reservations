import { Routes } from '@angular/router';
import { LoginComponent } from './authentication/login/login.component';
import { RegisterComponent } from './authentication/register/register.component';
import { RegisterSuccessComponent } from './authentication/register-success/register-success.component';
import { PasswordChangeComponent } from './authentication/password-change/password-change.component';
import { AdminComponent } from './admin/admin.component';
import { ProfileComponent } from './profile/profile.component';
import { authGuard } from './guards/auth.guard';
import { MyCottagesComponent } from './my-cottages/my-cottages.component';
import { AllCottagesComponent } from './all-cottages/all-cottages.component';
import { HomeComponent } from './home/home.component';
import { CottageComponent } from './cottage/cottage.component';
import { TouristReservationsComponent } from './tourist-reservations/tourist-reservations.component';
import { HostReservationsComponent } from './host-reservations/host-reservations.component';

export const routes: Routes = [
  {path: '', component: HomeComponent},
  {path: 'admin/login', component: LoginComponent},
  {path: 'login', component: LoginComponent},
  {path: 'register', component: RegisterComponent},
  {path: 'register-success', component: RegisterSuccessComponent},
  {path: 'password-change', component: PasswordChangeComponent, canActivate: [authGuard]},
  {path: 'profile', component: ProfileComponent, canActivate: [authGuard]},
  {path: 'all-cottages', component: AllCottagesComponent, canActivate: [authGuard]},
  {path: 'cottage/:id', component: CottageComponent, canActivate: [authGuard]},
  {path: 'my-cottages', component: MyCottagesComponent, canActivate: [authGuard]},
  {path: 'admin/dashboard', component: AdminComponent, canActivate: [authGuard]},
  {path: 'tourist-reservations', component: TouristReservationsComponent, canActivate: [authGuard]},
  {path: 'host-reservations', component: HostReservationsComponent, canActivate: [authGuard]}
];
