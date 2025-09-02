import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { MakeReservation } from '../models/requests/makeReservation';
import { ReservationResponse } from '../models/responses/reservationResponse';
import { catchError, throwError } from 'rxjs';
import { ReservationStatusUpdate } from '../models/requests/reservationStatusUpdate';

@Injectable({
  providedIn: 'root'
})
export class ReservationService {

  constructor(private http: HttpClient) { }

  private baseBackPath = 'http://localhost:8080';
  private curBackPath = this.baseBackPath + '/reservation';

  createReservation(makeReservation: MakeReservation) {
    return this.http.post<ReservationResponse>(`${this.curBackPath}/create`, makeReservation).pipe(
      catchError((error: HttpErrorResponse) => {
        let message = 'Непозната грешка при креирању резервације.';

        if (error.error?.messages) {
          message = error.error.messages.map((item: any) => item.message).join(', ');
        } else if (error.error?.message) {
          message = error.error.message;
        }

        return throwError(() => new Error(message));
      })
    );
  }

  getByTourist(touristId: number) {
    return this.http.get<ReservationResponse[]>(`${this.curBackPath}/getByTourist/${touristId}`);
  }

  getByHost(hostId: number) {
    return this.http.get<ReservationResponse[]>(`${this.curBackPath}/getByHost/${hostId}`);
  }

  cancel(reservation: ReservationResponse) {
    return this.http.put<ReservationResponse>(`${this.curBackPath}/cancel`, {
      id: reservation.id
    }).pipe(
      catchError((error: HttpErrorResponse) => {
        let message = error.error?.message || 'Непозната грешка при отказивању резервације';
        return throwError(() => new Error(message));
      })
    );
  }

  accept(reservationId: number) {
    return this.http.put<ReservationResponse>(`${this.curBackPath}/accept`, {
      id: reservationId
    }).pipe(
      catchError((error: HttpErrorResponse) => {
        let message = error.error?.message || 'Непозната грешка при прихватања резервације';
        return throwError(() => new Error(message));
      })
    );
  }

  reject(reservationId: number, rejectionComment: string) {
    return this.http.put<ReservationResponse>(`${this.curBackPath}/reject`, {
      id: reservationId,
      rejectionComment: rejectionComment
    }).pipe(
      catchError((error: HttpErrorResponse) => {
        let message = error.error?.message || 'Непозната грешка при одбијању резервације';
        return throwError(() => new Error(message));
      })
    );
  }

  rate(reservationId: number, rating: number, comment: string|null) {
    const data = comment ? {
      reservationId: reservationId,
      rating: rating,
      comment: comment
    } : {
      reservationId: reservationId,
      rating: rating
    }
    return this.http.post<ReservationResponse>(`${this.curBackPath}/rate`, data).pipe(
      catchError((error: HttpErrorResponse) => {
        let message = 'Непозната грешка при креирању рецензије.';

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
