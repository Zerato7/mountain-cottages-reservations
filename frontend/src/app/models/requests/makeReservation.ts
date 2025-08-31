export enum ReservationStatus {
  PENDING = 'PENDING',
  ACCEPTED_BY_OWNER = 'ACCEPTED_BY_OWNER',
  CANCELLED_BY_TOURIST = 'CANCELLED_BY_TOURIST',
  REJECTED_BY_OWNER = 'REJECTED_BY_OWNER'
}

export class MakeReservation {
  adults: number = 0;
  children: number = 0;
  creditCardNumber: string | null = null;
  endDateTime: string = ''
  startDateTime: string = ''
  specialDemands: string | null = null;
  cottageId: number = 0;
  touristId: number = 0;
  cost: number = 0.0;
}
