import { CommonModule } from '@angular/common';
import { Component, Input } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { ReservationService } from '../services/reservation.service';

@Component({
  selector: 'app-calendar-pop-up',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './calendar-pop-up.component.html',
  styleUrl: './calendar-pop-up.component.css'
})
export class CalendarPopUpComponent {

  constructor(
    public activeModal: NgbActiveModal,
    private reservationService: ReservationService
  ) { }

  @Input() reservationId: number = 0;

  acceptErrMsg: string = '';
  rejectErrMsg: string = '';
  rejectionComment: string = '';

  accept(): void {
    this.reservationService.accept(this.reservationId).subscribe({
      next: r => {
        this.activeModal.close();
      },
      error: err => {
        this.acceptErrMsg = err.message;
      }
    });
  }

  reject(): void {
    if (!this.rejectionComment) {
      this.rejectErrMsg = 'Коментар при одбијању је обавезан.';
      return;
    }

    this.reservationService.reject(this.reservationId, this.rejectionComment).subscribe({
      next: r => {
        this.activeModal.close();
      },
      error: err => {
        this.rejectErrMsg = err.message;
      }
    });
  }

}
