import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { NonAdminResponse } from '../models/responses/nonadminResponse';
import { catchError, throwError } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class NonadminService {

  constructor(private http: HttpClient) { }

  private baseBackPath = 'http://localhost:8080'
  private curBackPath = this.baseBackPath + '/nonadmin';

  getAll() {
    return this.http.get<NonAdminResponse[]>(`${this.curBackPath}/getAll`);
  }

  editNonadmin(editFormData: FormData) {
    return this.http.put<string>(`${this.curBackPath}/edit`, editFormData).pipe(
      catchError((error: HttpErrorResponse) => {
        let message = 'Непозната грешка при измени података корисника.';

          if (error.error?.messages) {
            message = error.error.messages.map((item: any) => item.message).join(', ');
          } else if (error.error?.message) {
            message = error.error.message;
          }

          return throwError(() => new Error(message));
      })
    );
  }

}
