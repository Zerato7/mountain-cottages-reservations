import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { ReservationResponse } from '../models/responses/reservationResponse';
import { BgColourUtil } from '../utils/bg-colour.util';
import { AuthService } from '../services/auth.service';
import { ReservationService } from '../services/reservation.service';
import { NonAdminResponse } from '../models/responses/nonadminResponse';
import { ReservationStatus } from '../models/requests/makeReservation';
import { FormsModule } from '@angular/forms';

type ReservationExt = {
  reservation: ReservationResponse,
  acceptErrMsg: string,
  rejectionComment: string
  rejectErrMsg: string
}

@Component({
  selector: 'app-host-reservations',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './host-reservations.component.html',
  styleUrl: './host-reservations.component.css'
})
export class HostReservationsComponent {

  constructor(
    private authService: AuthService,
    private reservationService: ReservationService
  ) { }

  host!: NonAdminResponse;
  pendingReservations: ReservationExt[] = [];
  cancelledReservations: ReservationResponse[] = [];
  denyedReservations: ReservationResponse[] = [];
  acceptedReservations: ReservationResponse[] = [];

  ngOnInit(): void {
    this.loadNonadmin();
    this.loadReservations();
  }

  loadNonadmin() {
    this.host = this.authService.getNonadmin()!;
  }

  loadReservations() {
    this.reservationService.getByHost(this.host.id).subscribe({
      next: reservations => {
        console.log(reservations)
        reservations.forEach((r) => {
          r.datetimeStart = new Date(r.datetimeStart);
          r.datetimeEnd = new Date(r.datetimeEnd);
        });
        reservations.sort((a, b) => {
          return b.datetimeStart.getTime() - a.datetimeStart.getTime()
        });
        const today = new Date();
        this.pendingReservations = [];
        reservations.filter((reservation) => {
          if (reservation.status == ReservationStatus.PENDING)
              return true;
          return false;
        }).forEach((r) => {
          this.pendingReservations.push({
            reservation: r,
            acceptErrMsg: '',
            rejectionComment: '',
            rejectErrMsg: ''
          });
        });
        this.cancelledReservations = reservations.filter((reservation) => reservation.status == ReservationStatus.CANCELLED_BY_TOURIST);
        this.denyedReservations = reservations.filter((reservation) => reservation.status == ReservationStatus.REJECTED_BY_OWNER);
        this.acceptedReservations = reservations.filter((reservation) => {
          if (reservation.status == ReservationStatus.ACCEPTED_BY_OWNER)
              return true;
          return false;
        });
      },
      error: err => {

      }
    });
  }

  acceptReservation(rExt: ReservationExt): void {
    this.reservationService.accept(rExt.reservation).subscribe({
      next: r => {
        this.loadReservations();
      },
      error: err => {
        rExt.acceptErrMsg = err.message;
      }
    });
  }

  rejectReservation(rExt: ReservationExt) {
    if (!rExt.rejectionComment) {
      rExt.rejectErrMsg = 'Коментар при одбијању је обавезан.';
      return;
    }

    this.reservationService.reject(rExt.reservation, rExt.rejectionComment).subscribe({
      next: r => {
        this.loadReservations();
      },
      error: err => {
        rExt.rejectErrMsg = err.message;
      }
    });
  }

  getBgClass(reservation: ReservationResponse): string {
    return BgColourUtil.getReservationTableRowBgClass(reservation.status);
  }

}
