import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { catchError, throwError } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class AdminService {

  constructor(private http: HttpClient) { }

  private baseBackPath = 'http://localhost:8080';
  private curBackPath = this.baseBackPath + '/admin';

  acceptNonadmin(id: number) {
    return this.http.put<string>(`${this.curBackPath}/accept/${id}`, {}).pipe(
      catchError((error: HttpErrorResponse) => {
        let message = error.error?.message || 'Непозната грешка при прихватању регистрације.';
        return throwError(() => new Error(message));
      })
    );
  }

  rejectNonadmin(id: number) {
    return this.http.put<string>(`${this.curBackPath}/reject/${id}`, {}).pipe(
      catchError((error: HttpErrorResponse) => {
        let message = error.error?.message || 'Непозната грешка при одбијању регистрације.';
        return throwError(() => new Error(message));
      })
    );
  }

  deactivateNonadmin(id: number) {
    return this.http.put<string>(`${this.curBackPath}/deactivate/${id}`, {}).pipe(
      catchError((error: HttpErrorResponse) => {
        let message = error.error?.message || 'Непозната грешка при деактивирању корисника.';
        return throwError(() => new Error(message));
      })
    );
  }

  reactivateNonadmin(id: number) {
    return this.http.put<string>(`${this.curBackPath}/reactivate/${id}`, {}).pipe(
      catchError((error: HttpErrorResponse) => {
        let message = error.error?.message || 'Непозната грешка при поновном активирању корисника.';
        return throwError(() => new Error(message));
      })
    );
  }

}
