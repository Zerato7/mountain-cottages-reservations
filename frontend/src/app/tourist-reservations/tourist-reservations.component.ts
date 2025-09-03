import { Component } from '@angular/core';
import { AuthService } from '../services/auth.service';
import { ReservationResponse } from '../models/responses/reservationResponse';
import { ReservationService } from '../services/reservation.service';
import { NonAdminResponse } from '../models/responses/nonadminResponse';
import { CommonModule, DatePipe } from '@angular/common';
import { DateTimeUtil } from '../utils/datetime.util';
import { ReservationStatus } from '../models/requests/makeReservation';
import { BgColourUtil } from '../utils/bg-colour.util';
import { NgbRatingModule } from '@ng-bootstrap/ng-bootstrap';
import { FormsModule } from "@angular/forms";

@Component({
  selector: 'app-tourist-reservations',
  standalone: true,
  imports: [CommonModule, DatePipe, NgbRatingModule, FormsModule],
  templateUrl: './tourist-reservations.component.html',
  styleUrl: './tourist-reservations.component.css'
})
export class TouristReservationsComponent {

  constructor(
    private authService: AuthService,
    private reservationService: ReservationService
  ) { }

  tourist!: NonAdminResponse;
  activeReservations: ReservationResponse[] = [];
  cancelledReservations: ReservationResponse[] = [];
  denyedReservations: ReservationResponse[] = [];
  arhivedReservations: ReservationResponse[] = [];

  ngOnInit(): void {
    this.loadNonadmin();
    this.loadReservations();
  }

  loadNonadmin() {
    this.tourist = this.authService.getNonadmin()!;
  }

  loadReservations() {
    this.reservationService.getByTourist(this.tourist.id).subscribe({
      next: reservations => {
        reservations.forEach((r) => {
          r.datetimeStart = new Date(r.datetimeStart);
          r.datetimeEnd = new Date(r.datetimeEnd);
        });
        const today = new Date();
        this.activeReservations = reservations.filter((reservation) => {
          if (reservation.datetimeStart.getTime() > today.getTime() &&
              (reservation.status == ReservationStatus.PENDING || reservation.status == ReservationStatus.ACCEPTED_BY_OWNER))
              return true;
          return false;
        });
        this.cancelledReservations = reservations.filter((reservation) => reservation.status == ReservationStatus.CANCELLED_BY_TOURIST);
        this.denyedReservations = reservations.filter((reservation) => reservation.status == ReservationStatus.REJECTED_BY_OWNER);
        this.arhivedReservations = reservations.filter((reservation) => {
          if (reservation.datetimeEnd.getTime() <= today.getTime() &&
              (reservation.status == ReservationStatus.PENDING || reservation.status == ReservationStatus.ACCEPTED_BY_OWNER))
              return true;
          return false;
        });
        this.arhivedReservations.sort((a, b) => {
          return b.datetimeStart.getTime() - a.datetimeStart.getTime()
        })
        console.log(this.activeReservations)
      },
      error: err => {

      }
    })
  }

  canCancel(reservation: ReservationResponse): boolean {
    console.log(DateTimeUtil.DateTimeDiffDays(reservation.datetimeStart, new Date()))
    return DateTimeUtil.DateTimeDiffDays(reservation.datetimeStart, new Date()) >= 1;
  }

  cancel(reservation: ReservationResponse): void {
    if (!this.canCancel(reservation)) return;

    this.reservationService.cancel(reservation).subscribe({
      next: r => {
        this.loadReservations();
      },
      error: err => {

      }
    })
  }

  getBgClass(reservation: ReservationResponse): string {
    return BgColourUtil.getReservationTableRowBgClass(reservation.status);
  }

  setFeedback(reservation: ReservationResponse): void {
    this.reservationService.rate(reservation.id, reservation.newRating, reservation.newComment).subscribe({
      next: r => {
        reservation.feedback = r.feedback;
      },
      error: err => {
        reservation.newRateErrMsg = err.message;
      }
    })
  }

}
