import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { ReservationResponse } from '../models/responses/reservationResponse';
import { BgColourUtil } from '../utils/bg-colour.util';
import { AuthService } from '../services/auth.service';
import { ReservationService } from '../services/reservation.service';
import { NonAdminResponse } from '../models/responses/nonadminResponse';
import { ReservationStatus } from '../models/requests/makeReservation';
import { FormsModule } from '@angular/forms';
import { FullCalendarModule } from '@fullcalendar/angular';
import { CalendarOptions, EventClickArg } from '@fullcalendar/core/index.js';
import dayGridPlugin from '@fullcalendar/daygrid';
import interactionPlugin from '@fullcalendar/interaction';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { CalendarPopUpComponent } from '../calendar-pop-up/calendar-pop-up.component';

type ReservationExt = {
  reservation: ReservationResponse,
  acceptErrMsg: string,
  rejectionComment: string
  rejectErrMsg: string
}

@Component({
  selector: 'app-host-reservations',
  standalone: true,
  imports: [CommonModule, FormsModule, FullCalendarModule],
  templateUrl: './host-reservations.component.html',
  styleUrl: './host-reservations.component.css'
})
export class HostReservationsComponent {

  constructor(
    private authService: AuthService,
    private reservationService: ReservationService,
    private modalService: NgbModal
  ) { }

  host!: NonAdminResponse;
  pendingReservations: ReservationExt[] = [];
  cancelledReservations: ReservationResponse[] = [];
  denyedReservations: ReservationResponse[] = [];
  acceptedReservations: ReservationResponse[] = [];
  calendarOptions: CalendarOptions = {
    plugins: [dayGridPlugin, interactionPlugin],
    initialView: 'dayGridMonth',
    events: [],
    eventClick: this.handleEventClick.bind(this)
  };

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
        this.calendarOptions.events = [];
        this.calendarOptions.events = reservations
          .filter(r => r.status !== ReservationStatus.CANCELLED_BY_TOURIST)
          .map(r => ({
            id: r.status === ReservationStatus.PENDING ? r.id.toString() : (-r.id).toString(),
            title: r.touristFirstname + ' ' + r.touristLastname +
              ' - ' + r.cottageName,
            start: r.datetimeStart,
            end: r.datetimeEnd,
            color: r.status === ReservationStatus.PENDING ? '#cd9a3c' :
              r.status === ReservationStatus.ACCEPTED_BY_OWNER ? '#3a7855' : '#b84242'
        }));
        this.calendarOptions = {
          ...this.calendarOptions,
          eventContent: (arg) => {
            return { html: arg.isStart ? `<span>${arg.event.title}</span>` : '&nbsp;' };
          }
        };
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
    this.reservationService.accept(rExt.reservation.id).subscribe({
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

    this.reservationService.reject(rExt.reservation.id, rExt.rejectionComment).subscribe({
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

  handleEventClick(clickInfo: EventClickArg) {
    if (clickInfo.event.id === '-1') return;

    const modalRef = this.modalService.open(CalendarPopUpComponent, {
      centered: true,
      size: 'sm'
    });
    modalRef.componentInstance.reservationId = parseInt(clickInfo.event.id);

    modalRef.result.then(
      () => {
        this.loadReservations();
      },
      () => {

      }
    );
  }

}
