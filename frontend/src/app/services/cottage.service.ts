import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { CottageResponse } from '../models/responses/cottageResponse';
import { catchError, throwError } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class CottageService {

  constructor(private http: HttpClient) { }

  private baseBackPath = 'http://localhost:8080';
  private curBackPath = this.baseBackPath + '/cottage';

  getAll() {
    return this.http.get<CottageResponse[]>(`${this.curBackPath}/getAll`);
  }

  getMy(ownerId: number) {
    return this.http.get<CottageResponse[]>(`${this.curBackPath}/getMy/${ownerId}`);
  }

  createCottage(formData: FormData) {
    return this.http.post<CottageResponse>(`${this.curBackPath}/create`, formData).pipe(
      catchError((error: HttpErrorResponse) => {
        let message = 'Непозната грешка при креирању викендице.';

          if (error.error?.messages) {
            message = error.error.messages.map((item: any) => item.message).join(', ');
          } else if (error.error?.message) {
            message = error.error.message;
          }

          return throwError(() => new Error(message));
      })
    );
  }

  deleteCottage(id: number) {
    return this.http.delete<string>(`${this.curBackPath}/delete/${id}`).pipe(
      catchError((error: HttpErrorResponse) => {
        let message = error.error?.message || 'Непозната грешка при брисању викендице.';
        return throwError(() => new Error(message));
      })
    );
  }

}
