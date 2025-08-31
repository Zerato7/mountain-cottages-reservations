import { CommonModule, DatePipe } from '@angular/common';
import { Component, Input } from '@angular/core';
import { FormControl, FormGroup, ReactiveFormsModule,  Validators } from '@angular/forms';
import { CreditCardUtil } from '../utils/credit-card.util';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { OwlDateTimeModule, OwlNativeDateTimeModule } from '@danielmoncada/angular-datetime-picker';
import { DateTimeUtil } from '../utils/datetime.util';
import { NonAdminResponse } from '../models/responses/nonadminResponse';
import { CottageResponse } from '../models/responses/cottageResponse';
import { ParseUtil } from '../utils/parse.util';
import { ReservationService } from '../services/reservation.service';
import { MakeReservation } from '../models/requests/makeReservation';

@Component({
  selector: 'app-make-reservation',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule, OwlDateTimeModule, OwlNativeDateTimeModule, DatePipe],
  templateUrl: './make-reservation.component.html',
  styleUrl: './make-reservation.component.css'
})
export class MakeReservationComponent {

  constructor(
    public activeModal: NgbActiveModal,
    private reservationService: ReservationService
  ) { }

  @Input() nonadmin: NonAdminResponse = new NonAdminResponse();
  @Input() cottage: CottageResponse = new CottageResponse();

  step: number = 1;
  errorMessage: string = '';
  cardType: string | null = null;

  form1!: FormGroup;
  form2!: FormGroup;

  cost: number = 0;

  ngOnInit(): void {
    let initialStartDateTime = new Date(new Date().getTime() + DateTimeUtil.oneDayMS);
    initialStartDateTime.setHours(14, 0, 0, 0);
    let initialEndDateTime = new Date(initialStartDateTime.getTime() + DateTimeUtil.oneDayMS);
    initialEndDateTime.setHours(9, 59, 0, 0);
    this.form1 = new FormGroup({
      startDateTime: new FormControl<Date>(initialStartDateTime, {
        nonNullable: true,
        validators: [
          Validators.required,
          DateTimeUtil.minTodayValidator
        ]
      }),
      endDateTime: new FormControl<Date>(initialEndDateTime, {
        nonNullable: true,
        validators: [
          Validators.required
        ]
      }),
      adults: new FormControl<number>(1, {
        validators: [
          ParseUtil.Validators.isInteger(),
          Validators.required,
          ParseUtil.Validators.minValue(1)
        ]
      }),
      children: new FormControl<number>(0, {
        nonNullable: true,
        validators: [
          ParseUtil.Validators.isInteger(),
          Validators.required,
          ParseUtil.Validators.minValue(0)
        ]
      })
    }, {
      validators: DateTimeUtil.MinStartDateTimeValidator
    });

    this.form2 = new FormGroup({
      creditCardNumber: new FormControl<string|null>(
        null, {
        nonNullable: true,
        validators: [
          CreditCardUtil.creditCardNumberValidator
        ]
      }),
      specialDemands: new FormControl<string|null>(null, {
        nonNullable: false,
        validators: [
          Validators.maxLength(500)
        ]
      })
    });

    this.form2.get('creditCardNumber')?.valueChanges.subscribe(() => {
      this.cardType = CreditCardUtil.getCardType(
        this.form2.get('creditCardNumber')?.value || ''
      );
    });
  }

  next(): void {
    this.step = 2;
    const nightNum: number = DateTimeUtil.DateTimeDiffDays(this.form1.get('endDateTime')?.value, this.form1.get('startDateTime')?.value);
    console.log(nightNum);
    if (true) {
      this.cost = this.cottage.summerPriceAdult * this.form1.get('adults')?.value +
        this.cottage.summerPriceChild * this.form1.get('children')?.value;
      this.cost *= nightNum;
    } else {
      this.cost = this.cottage.winterPriceAdult * this.form1.get('adults')?.value +
        this.cottage.winterPriceChild * this.form1.get('children')?.value;
      this.cost *= nightNum;
    }
  }

  getCreditCardDisplay(): string {
    return CreditCardUtil.getCreditCardNumberDisplay(this.nonadmin.creditCardNumberLast4Digits)
  }

  makeReservation(): void {
    const form1Values = this.form1.value;
    const form2Values = this.form2.value;
    let dto = new MakeReservation();
    dto.adults = form1Values.adults;
    dto.children = form1Values.children;
    dto.creditCardNumber = form2Values.creditCardNumber;
    dto.endDateTime = form1Values.endDateTime;
    dto.startDateTime = form1Values.startDateTime;
    dto.specialDemands = form2Values.specialDemands;
    dto.cottageId = this.cottage.id;
    dto.touristId = this.nonadmin.id;
    dto.cost = this.cost;

    this.reservationService.createReservation(dto).subscribe({
      next: reservation => {
        this.activeModal.close(true);
      },
      error: err => {
        this.errorMessage = err.message;
      }
    })
  }

}
