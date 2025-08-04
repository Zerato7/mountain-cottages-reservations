import { inject, Injectable } from '@angular/core';
import { NonAdminResponse } from '../models/responses/nonadminResponse';
import { UserType } from '../models/userType';
import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { UserLogin } from '../models/requests/userLogin';
import { catchError, throwError } from 'rxjs';
import { UserRegistration } from '../models/requests/userRegistration';
import { PasswordChange } from '../models/requests/passwordChange';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  constructor() {
    this.loadNonAdminFromStorage();
  }

  private nonadmin: NonAdminResponse | null = null;

  private loadNonAdminFromStorage(): void {
    const nonadminJson = localStorage.getItem('loggedInNonAdmin');
    this.nonadmin = nonadminJson ? JSON.parse(nonadminJson) : null;
  }

  getUser(): NonAdminResponse | null {
    return this.nonadmin;
  }

  isLoggedIn(): boolean {
    return this.nonadmin !== null;
  }

  getUserType(): UserType | null{
    return this.nonadmin ? this.nonadmin.userType : null;
  }

  setUser(nonadmin: NonAdminResponse): void {
    this.nonadmin = nonadmin;
    localStorage.setItem('loggedInNonAdmin', JSON.stringify(nonadmin));
  }

  logout(): void {
    this.nonadmin = null;
    localStorage.removeItem('loggedInNonAdmin');
  }

  private http = inject(HttpClient);
  private backPath = 'http://localhost:8080/nonadmin';

  login(userLogin: UserLogin) {
    return this.http.post<NonAdminResponse>(`${this.backPath}/login`, userLogin).pipe(
      catchError((error: HttpErrorResponse) => {
        let message = error.error?.message || 'Непозната грешка при пријави';
        return throwError(() => new Error(message));
      })
    );
  }

  register(userRegistration: UserRegistration) {
    return this.http.post<string>(`${this.backPath}/register`, userRegistration).pipe(
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
    return this.http.put<string>(`${this.backPath}/change-password`, passwordChange).pipe(
      catchError((error: HttpErrorResponse) => {
        let message = 'Непозната грешка при промени лозинке.';
        if (error.error?.message) {
          message = error.error.message;
        }
        return throwError(() => new Error(message));
      })
    );
  }

}
