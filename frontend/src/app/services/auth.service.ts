import { inject, Injectable } from '@angular/core';
import { NonAdminResponse } from '../models/responses/nonadminResponse';
import { UserType } from '../models/userType';
import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { UserLogin } from '../models/requests/userLogin';
import { catchError, throwError } from 'rxjs';
import { UserRegistration } from '../models/requests/userRegistration';
import { PasswordChange } from '../models/requests/passwordChange';
import { UserResponse } from '../models/responses/userResponse';

const keyLoggedName = 'loggedInUser';
const authUrls = ['/login', '/register', '/admin/login'];

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  constructor(private http: HttpClient) {
    this.loadNonAdminFromStorage();
  }

  private user: UserResponse | null = null;
  private nonadmin: NonAdminResponse | null = null;

  private baseBackPath = 'http://localhost:8080'
  private curBackPath = this.baseBackPath + '/auth';

  private loadNonAdminFromStorage(): void {
    const nonadminJson = localStorage.getItem(keyLoggedName);
    this.nonadmin = nonadminJson ? JSON.parse(nonadminJson) : null;
    this.user = this.nonadmin;
  }

  getUser(): UserResponse | null {
    return this.user;
  }

  getNonadmin(): NonAdminResponse | null {
    return this.nonadmin;
  }

  isLoggedIn(): boolean {
    return this.user !== null;
  }

  getUserType(): UserType | null{
    return this.user ? this.user.userType : null;
  }

  setNonadmin(nonadmin: NonAdminResponse): void {
    this.nonadmin = nonadmin;
    this.user = nonadmin;
    localStorage.setItem(keyLoggedName, JSON.stringify(nonadmin));
  }

  setAdmin(admin: UserResponse): void {
    this.user = admin;
    localStorage.setItem(keyLoggedName, JSON.stringify(admin));
  }

  logout(): void {
    this.nonadmin = null;
    this.user = null;
    localStorage.removeItem(keyLoggedName);
  }

  isAuthPage(url: string): boolean {
    return authUrls.includes(url);
  }

  loginNonadmin(userLogin: UserLogin) {
    return this.http.post<NonAdminResponse>(`${this.curBackPath}/login`, userLogin).pipe(
      catchError((error: HttpErrorResponse) => {
        let message = error.error?.message || 'Непозната грешка при пријави';
        return throwError(() => new Error(message));
      })
    );
  }

  loginAdmin(userLogin: UserLogin) {
    return this.http.post<UserResponse>(`${this.baseBackPath}/admin/login`, userLogin).pipe(
      catchError((error: HttpErrorResponse) => {
        let message = error.error?.message || 'Непозната грешка при пријави';
        return throwError(() => new Error(message));
      })
    );
  }

  register(registartionFormData: FormData) {
    return this.http.post<string>(`${this.curBackPath}/register`, registartionFormData).pipe(
      catchError((error: HttpErrorResponse) => {
        //console.log(error);
        let message = 'Непозната грешка при регистрацији.';

        if (error.error?.messages) {
          message = error.error.messages.map((item: any) => item.message).join(', ');
        } else if (error.error?.message) {
          message = error.error.message;
        }

        return throwError(() => new Error(message));
      })
    );
  }

  changePassword(passwordChange: PasswordChange) {
    return this.http.put<string>(`${this.curBackPath}/change-password`, passwordChange).pipe(
      catchError((error: HttpErrorResponse) => {
        //console.log(error);
        let message = 'Непозната грешка при промени лозинке.';
        if (error.error?.message) {
          message = error.error.message;
        }
        return throwError(() => new Error(message));
      })
    );
  }

}
